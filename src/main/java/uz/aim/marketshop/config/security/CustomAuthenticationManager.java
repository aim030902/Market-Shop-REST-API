package uz.aim.marketshop.config.security;

import uz.aim.marketshop.domains.auth.AuthUser;
import uz.aim.marketshop.exceptions.GenericNotFoundException;
import uz.aim.marketshop.exceptions.GenericRuntimeException;
import uz.aim.marketshop.repository.auth.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationProvider {

    private final PasswordEncoder encoder;
    private final AuthUserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthUser authUser = repository.findByUsername(username).orElseThrow(() -> {
            throw new GenericNotFoundException("Invalid username or password!", 404);
        });
        if (!encoder.matches(password, authUser.getPassword())) {
            String message;
            int statusCode;
            authUser.setLoginTryCount(authUser.getLoginTryCount() + 1);
            if (authUser.getLoginTryCount() == 3) {
                message = "You are blocked! Try 2 minutes later!";
                statusCode = 403;
                authUser.setStatus(AuthUser.Status.BLOCKED);
                authUser.setLastLoginTime(LocalDateTime.now());
            } else {
                message = "Invalid username or password!";
                statusCode = 400;
            }
            repository.save(authUser);
            throw new GenericRuntimeException(message, statusCode);
        }
        return new UsernamePasswordAuthenticationToken(new UserDetails(authUser), authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
