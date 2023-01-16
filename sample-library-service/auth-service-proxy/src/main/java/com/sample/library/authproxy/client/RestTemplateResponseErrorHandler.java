package com.sample.library.authproxy.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.library.authproxy.datatransferobject.AuthServerErrorMessage;
import com.sample.library.authproxy.exception.AuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.Charset;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (httpResponse.getStatusCode().is4xxClientError()
                || httpResponse.getStatusCode().is5xxServerError());
    }


    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        Charset charset = getCharset(httpResponse);

        if (charset == null) {
            charset = Charset.defaultCharset();
        }

        String s = new String(getResponseBody(httpResponse), charset);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AuthServerErrorMessage authServerErrorMessage = mapper.readValue(s, AuthServerErrorMessage.class);

        throw new AuthException(authServerErrorMessage.getMessage());
    }


    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return (contentType != null ? contentType.getCharset() : null);
    }


    protected byte[] getResponseBody(ClientHttpResponse response) {
        try {
            return FileCopyUtils.copyToByteArray(response.getBody());
        } catch (IOException ex) {
            // ignore
        }
        return new byte[0];
    }
}
