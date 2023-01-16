package com.sample.library.configuration;

import com.sample.library.utility.StringUtility;
import lombok.SneakyThrows;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineDecryptionHandler {

    private final String[] args;
    private boolean passwordIsManuallyRequired;
    private Map<String, String> encryptedProperties;
    private boolean passwordIsRequired;
    private String passwordStr;
    private StandardPBEStringEncryptor encryptor;

    public CommandLineDecryptionHandler(String[] args) {
        this.args = args;

        findPasswordIsManuallyRequired();
        prepareEncryptedProperties();

        passwordIsRequired = !encryptedProperties.isEmpty() || passwordIsManuallyRequired;

        if (passwordIsRequired) {
            extractPassword();
            provideEncryptor();
            decryptProperties();
        }
    }

    private static String tryToGetValue(Supplier<String> supplier) {
        System.out.print("Enter password: ");

        String result = null;
        do {
            result = supplier.get();
        } while (result.isEmpty());
        return result;
    }

    @SneakyThrows(Exception.class)
    private static String readKeyboardEntryBuffer() {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    public String[] getApplicationArgs() {
        if (passwordIsRequired) {
            List<String> argsArray = new ArrayList(Arrays.asList(args));
            argsArray.add("--com.modernisc.jasypt.pass=" + passwordStr);
            return argsArray.toArray(new String[argsArray.size()]);
        } else {
            return args;
        }
    }

    private void extractPassword() {
        String commandLinePassword = System.getProperty("com.modernisc.jasypt.pass");
        if (!commandLinePassword.isEmpty()) {
            passwordStr = commandLinePassword;
        } else {
            if (System.console() != null) {
                passwordStr = tryToGetValue(() -> new String(System.console().readPassword()));
            } else {
                passwordStr = tryToGetValue(CommandLineDecryptionHandler::readKeyboardEntryBuffer);
            }
        }
    }

    private void findPasswordIsManuallyRequired() {
        //reading password if intended via --get-password command line option or use the default included in properties
        SimpleCommandLinePropertySource simpleCommandLinePropertySource = new SimpleCommandLinePropertySource(args);
        if (!simpleCommandLinePropertySource.containsProperty("get-password")) {
            passwordIsManuallyRequired = false;
            return;
        }

        String getPasswordStrValue = simpleCommandLinePropertySource.getProperty("get-password");
        passwordIsManuallyRequired = StringUtility.isNullOrEmpty(getPasswordStrValue) || Boolean.parseBoolean(getPasswordStrValue);
    }

    private void prepareEncryptedProperties() {
        encryptedProperties = new HashMap<>();

        Enumeration<?> propertyNames = System.getProperties().propertyNames();
        Pattern encryptedPattern = Pattern.compile("^ENC\\((.*)\\)$");
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String) propertyNames.nextElement();

            String propertyValue = System.getProperty(propertyName);

            if (propertyValue == null) {
                continue;
            }

            Matcher matcher = encryptedPattern.matcher(propertyValue.trim());

            if (matcher.matches()) {

                encryptedProperties.put(propertyName, matcher.group(1));
            }
        }
    }

    private void decryptProperties() {
        Set<String> propertyNames = encryptedProperties.keySet();
        for (String propertyName : propertyNames) {

            String decryptedValue = encryptor.decrypt(encryptedProperties.get(propertyName));

            System.setProperty(propertyName, decryptedValue);
        }
    }

    private void provideEncryptor() {
        String encryptionAlgorithm = System.getProperty("com.modernisc.jasypt.algorithm");

        encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(encryptionAlgorithm);
        encryptor.setPassword(passwordStr);
    }
}
