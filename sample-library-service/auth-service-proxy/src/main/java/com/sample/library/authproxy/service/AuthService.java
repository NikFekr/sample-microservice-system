package com.sample.library.authproxy.service;

import com.sample.library.authproxy.datatransferobject.LoginRequest;
import com.sample.library.authproxy.datatransferobject.LoginResponse;

/**
 * @author Esmaeil NikFekr on 18.12.22
 */
public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
