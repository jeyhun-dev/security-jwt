package az.cmammad.security.service.impl;

import az.cmammad.security.dto.AccessTokenResponseDto;
import az.cmammad.security.dto.LoginRequestDto;
import az.cmammad.security.entity.UserEntity;
import az.cmammad.security.exception.PasswordIsWrongException;
import az.cmammad.security.exception.UserNotFoundException;
import az.cmammad.security.repository.UserRepository;
import az.cmammad.security.security.jwt.JwtService;
import az.cmammad.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccessTokenResponseDto signIn(final LoginRequestDto loginRequest) {
        final UserEntity user = checkUserEmail(loginRequest);
        checkUserPassword(loginRequest, user);
        final var accessToken = jwtService.generateAccessToken(user);
        final var createdDate = jwtService.getCreateDateTimeForJwt(accessToken);
        return AccessTokenResponseDto.builder()
                .accessToken(accessToken)
                .createdDate(createdDate)
                .build();
    }

    private UserEntity checkUserEmail(final LoginRequestDto loginRequest) {
        return userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkUserPassword(final LoginRequestDto loginRequest,
                                   final UserEntity user) {
        final boolean matches = passwordEncoder
                .matches(
                        loginRequest.getPassword(),
                        user.getPassword()
                );
        if (!matches) {
            throw new PasswordIsWrongException();
        }
    }
}
