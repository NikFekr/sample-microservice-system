package com.modernisc.servicediscovery;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineDecryptionHandler {

    private final String[] args;
    private boolean passwordIsManuallyRequired;
    private Map<String, String> encryptedProperties;
    private final boolean passwordIsRequired;
    private String passwordStr;
    private StandardPBEStringEncryptor encryptor;


    public CommandLineDecryptionHandler(String[] args) throws Exception {
        this.args = args;

        findPasswordIsManuallyRequired();
        prepareEncryptedProperties();

        passwordIsRequired = Objects.nonNull(encryptedProperties) || passwordIsManuallyRequired;

        if (passwordIsRequired) {
            extractPassword();
            provideEncryptor();
            decryptProperties();
        }
    }

    public String[] getApplicationArgs() {
        if (passwordIsRequired) {
            List<String> argsArray = new ArrayList(Arrays.asList(args));
            argsArray.add("--jasypt.encryptor.password=" + passwordStr);
            return argsArray.toArray(new String[argsArray.size()]);
        } else {
            return args;
        }
    }

    private void extractPassword() throws Exception {
        String commandLinePassword = System.getProperty("jasypt.encryptor.password");
        if (Objects.nonNull(commandLinePassword)) {
            passwordStr = commandLinePassword;
        } else {
            if (System.console() != null) {
                passwordStr = tryToGetValue(() -> new String(System.console().readPassword()));
            } else {
                passwordStr = tryToGetValue(() -> new BufferedReader(new InputStreamReader(System.in)).readLine());
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
        passwordIsManuallyRequired = Objects.isNull(getPasswordStrValue) ||
                getPasswordStrValue.trim().equals("") ||
                Boolean.parseBoolean(getPasswordStrValue);
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
        String encryptionAlgorithm = "PBEWITHSHA256AND128BITAES-CBC-BC";

        encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm(encryptionAlgorithm);
        encryptor.setPassword(passwordStr);
    }


    @FunctionalInterface
    private static interface Supplier<T> {
        T get() throws Exception;
    }

    private static String tryToGetValue(Supplier<String> supplier) throws Exception {
        System.out.print("Enter password: ");

        String result = null;
        do {
            result = supplier.get();
        } while (StringUtils.isEmpty(result));
        return result;
    }


}
