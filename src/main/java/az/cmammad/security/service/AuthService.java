package az.cmammad.security.service;

import az.cmammad.security.dto.AccessTokenResponseDto;
import az.cmammad.security.dto.LoginRequestDto;

public interface AuthService {

    AccessTokenResponseDto signIn(LoginRequestDto loginRequest);
}
