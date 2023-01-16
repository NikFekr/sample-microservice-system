package com.sample.library.mapper;

import com.sample.library.authproxy.datatransferobject.LoginResponse;
import com.sample.library.utility.Mapper;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public class LoginResponseMapper extends Mapper<LoginResponse, com.sample.library.datatransferobject.LoginResponse> {
    @Override
    public com.sample.library.datatransferobject.LoginResponse map(LoginResponse in) {
        return com.sample.library.datatransferobject.LoginResponse.builder()
                .accessToken(in.getAccessToken())
                .expiresIn(in.getExpiresIn())
                .refreshToken(in.getRefreshToken())
                .refreshExpiresIn(in.getRefreshExpiresIn())
                .build();
    }
}
