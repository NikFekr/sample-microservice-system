package com.sample.library.api.mapper;

import com.sample.library.datatransferobject.LoginResponse;
import com.sample.library.utility.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Esmaeil NikFekr on 5/25/21.
 */
@Component
public class LoginResponseMapper extends Mapper<LoginResponse,
        com.sample.library.api.consumerone.datatransferobject.LoginResponse> {


    @Override
    public com.sample.library.api.consumerone.datatransferobject.LoginResponse map(LoginResponse in) {
        return com.sample.library.api.consumerone.datatransferobject.LoginResponse.builder()
                .accessToken(in.getAccessToken())
                .expiresIn(in.getExpiresIn())
                .refreshToken(in.getRefreshToken())
                .refreshExpiresIn(in.getRefreshExpiresIn())
                .build();
    }
}
