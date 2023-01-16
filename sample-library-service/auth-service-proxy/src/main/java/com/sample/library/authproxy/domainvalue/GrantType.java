package com.sample.library.authproxy.domainvalue;

/**
 * @author Esmaeil NikFekr on 5/25/21.
 */
public enum GrantType {
    REFRESH_TOKEN("refresh_token"), PASSWORD("password");


    private String grantType;

    public String getGrantType() {
        return grantType;
    }

    GrantType(String grantType) {
        this.grantType = grantType;
    }
}
