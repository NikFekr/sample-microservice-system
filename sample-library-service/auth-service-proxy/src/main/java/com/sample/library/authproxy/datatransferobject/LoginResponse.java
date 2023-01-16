package com.sample.library.authproxy.datatransferobject;

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

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("not-before-policy")
    private String notBeforePolicy;

    @JsonProperty("session_state")
    private String sessionState;

    @JsonProperty("scope")
    private String scope;


    @Override
    public String toString() {
        return new StringJoiner(", ", LoginResponse.class.getSimpleName() + "[", "]")
                .add("accessToken='****'")
                .add("expiresIn='" + expiresIn + "'")
                .add("refreshExpiresIn='" + refreshExpiresIn + "'")
                .add("refreshToken='****'")
                .add("tokenType='" + tokenType + "'")
                .add("notBeforePolicy='" + notBeforePolicy + "'")
                .add("sessionState='****'")
                .add("scope='" + scope + "'")
                .toString();
    }
}
