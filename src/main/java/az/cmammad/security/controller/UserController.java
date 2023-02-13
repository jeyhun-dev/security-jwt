package az.cmammad.security.controller;

import az.cmammad.security.dto.UserRegisterRequestDto;
import az.cmammad.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signUp(@RequestBody @Valid UserRegisterRequestDto userRegister) {
        log.info("Create user body {}", userRegister);
        return new ResponseEntity<>(userService.signUp(userRegister), HttpStatus.CREATED);
    }
}
