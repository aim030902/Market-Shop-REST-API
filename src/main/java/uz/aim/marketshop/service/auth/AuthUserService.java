package uz.aim.marketshop.service.auth;

import uz.aim.marketshop.config.security.CustomAuthenticationManager;
import uz.aim.marketshop.config.security.UserDetails;
import uz.aim.marketshop.domains.auth.AuthUser;
import uz.aim.marketshop.dtos.JwtResponse;
import uz.aim.marketshop.dtos.LoginRequest;
import uz.aim.marketshop.dtos.RefreshTokenRequest;
import uz.aim.marketshop.dtos.UserRegisterDTO;
import uz.aim.marketshop.exceptions.GenericInvalidTokenException;
import uz.aim.marketshop.exceptions.GenericNotFoundException;
import uz.aim.marketshop.mapper.AuthUserMapper;
import uz.aim.marketshop.repository.auth.AuthUserRepository;
import uz.aim.marketshop.utils.jwt.TokenService;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Service
public class AuthUserService implements UserDetailsService {
    private final CustomAuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final TokenService accessTokenService;
    private final TokenService refreshTokenService;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(@Lazy CustomAuthenticationManager authenticationManager,
                           AuthUserRepository authUserRepository,
                           @Qualifier("accessTokenService") TokenService accessTokenService,
                           @Qualifier("refreshTokenService") TokenService refreshTokenService,
                           AuthUserMapper authUserMapper,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.authUserRepository = authUserRepository;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.authUserMapper = authUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> exception = () ->
                new UsernameNotFoundException("Bad credentials");
        AuthUser authUser = authUserRepository.findByUsername(username).orElseThrow(exception);
        return new UserDetails(authUser);
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = accessTokenService.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken(userDetails);
        AuthUser authUser = userDetails.authUser();
        authUser.setLastLoginTime(LocalDateTime.now());
        authUserRepository.save(authUser);
        return new JwtResponse(accessToken, refreshToken, "Bearer");
    }

    public JwtResponse refreshToken(@NonNull RefreshTokenRequest request) {
        String token = request.token();
        if (accessTokenService.isValid(token)) {
            throw new GenericInvalidTokenException("Refresh Token invalid", 401);
        }
        String subject = accessTokenService.getSubject(token);
        UserDetails userDetails = loadUserByUsername(subject);
        String accessToken = accessTokenService.generateToken(userDetails);
        return new JwtResponse(accessToken, request.token(), "Bearer");
    }

    @SneakyThrows
    @Transactional
    public AuthUser register(UserRegisterDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        AuthUser authUser = authUserMapper.fromRegisterDTO(dto);
        authUserRepository.save(authUser);
        return authUser;
    }

    public AuthUser getCurrentAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return authUserRepository.findByUsername(username).orElseThrow(() -> {
            throw new GenericNotFoundException("User not found!", 404);
        });
    }
}
