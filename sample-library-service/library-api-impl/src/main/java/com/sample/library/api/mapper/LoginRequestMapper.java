package com.sample.library.api.mapper;

import com.sample.library.api.consumerone.datatransferobject.LoginRequest;
import com.sample.library.utility.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Esmaeil NikFekr on 5/25/21.
 */
@Component
public class LoginRequestMapper extends Mapper<LoginRequest,
        com.sample.library.datatransferobject.LoginRequest> {


    @Override
    public com.sample.library.datatransferobject.LoginRequest map(LoginRequest in) {
        return com.sample.library.datatransferobject.LoginRequest.builder()
                .username(in.getUsername())
                .password(in.getPassword())
                .build();
    }
}
