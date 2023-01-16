package com.sample.library.mapper;

import com.sample.library.datatransferobject.LoginRequest;
import com.sample.library.utility.Mapper;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public class LoginRequestMapper extends Mapper<LoginRequest,
        com.sample.library.authproxy.datatransferobject.LoginRequest> {
    @Override
    public com.sample.library.authproxy.datatransferobject.LoginRequest map(LoginRequest in) {
        return com.sample.library.authproxy.datatransferobject.LoginRequest.builder()
                .username(in.getUsername())
                .password(in.getPassword())
                .build();
    }
}
