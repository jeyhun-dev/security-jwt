package az.cmammad.security.controller;

import az.cmammad.security.dto.AccessTokenResponseDto;
import az.cmammad.security.dto.LoginRequestDto;
import az.cmammad.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessTokenResponseDto> signIn(@RequestBody @Valid LoginRequestDto loginRequest) {
        log.info("User is login body {}", loginRequest);
        return new ResponseEntity<>(authService.signIn(loginRequest), HttpStatus.CREATED);
    }
}
