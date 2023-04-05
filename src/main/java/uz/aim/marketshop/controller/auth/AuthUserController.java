package uz.aim.marketshop.controller.auth;

import uz.aim.marketshop.controller.ApiController;
import uz.aim.marketshop.domains.auth.AuthUser;
import uz.aim.marketshop.dtos.JwtResponse;
import uz.aim.marketshop.dtos.LoginRequest;
import uz.aim.marketshop.dtos.RefreshTokenRequest;
import uz.aim.marketshop.dtos.UserRegisterDTO;
import uz.aim.marketshop.response.ApiResponse;
import uz.aim.marketshop.service.auth.AuthUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthUserController extends ApiController<AuthUserService> {
    protected AuthUserController(AuthUserService service) {
        super(service);
    }

    @PostMapping(value = PATH + "/auth/login", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ApiResponse<>(service.login(loginRequest));
    }

    @GetMapping(value = PATH + "/auth/refresh", produces = "application/json")
    @PreAuthorize(value = "isAuthenticated()")
    public ApiResponse<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ApiResponse<>(service.refreshToken(refreshTokenRequest));
    }

    @PostMapping(PATH + "/auth/register")
    public ApiResponse<AuthUser> register(@Valid @RequestBody UserRegisterDTO dto) {
        return new ApiResponse<>(service.register(dto));
    }

    @GetMapping(PATH + "/auth/me")
    @PreAuthorize(value = "isAuthenticated()")
    public AuthUser me() {
        return service.getCurrentAuthUser();
    }
}
