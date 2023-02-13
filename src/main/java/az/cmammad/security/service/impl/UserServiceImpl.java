package az.cmammad.security.service.impl;

import az.cmammad.security.dto.UserRegisterRequestDto;
import az.cmammad.security.entity.UserEntity;
import az.cmammad.security.exception.EmailAlreadyExistsException;
import az.cmammad.security.repository.UserRepository;
import az.cmammad.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signUp(final UserRegisterRequestDto userRegister) {
        if (existsEmailTaken(userRegister.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        final var user = UserEntity.builder()
                .firstname(userRegister.getFirstname())
                .lastname(userRegister.getLastname())
                .address(userRegister.getAddress())
                .phoneNumber(userRegister.getPhoneNumber())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .build();
        userRepository.save(user);
        return "User registered";
    }

    private boolean existsEmailTaken(final String email) {
        return userRepository.existsByEmail(email);
    }
}
