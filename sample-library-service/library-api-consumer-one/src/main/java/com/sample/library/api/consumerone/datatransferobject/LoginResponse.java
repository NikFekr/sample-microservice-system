package com.sample.library.api.consumerone.datatransferobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;

/**
 * @author Esmaeil NikFekr on 5/23/21.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("refresh_expires_in")
    private String refreshExpiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;


    @Override
    public String toString() {
        return new StringJoiner(", ", LoginResponse.class.getSimpleName() + "[", "]")
                .add("accessToken='****'")
                .add("expiresIn='" + expiresIn + "'")
                .add("refreshExpiresIn='" + refreshExpiresIn + "'")
                .add("refreshToken='****'")
                .toString();
    }
}
