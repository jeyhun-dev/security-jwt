package az.cmammad.security.security;

import az.cmammad.security.entity.UserEntity;
import az.cmammad.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return convertUser(userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(email)));
    }

    private User convertUser(final UserEntity user) {
        return new User(
                user.getEmail(),
                user.getPassword(),
                List.of()
        );
    }
}
