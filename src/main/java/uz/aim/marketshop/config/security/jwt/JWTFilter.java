package uz.aim.marketshop.config.security.jwt;

import uz.aim.marketshop.config.security.UserDetails;
import uz.aim.marketshop.service.auth.AuthUserService;
import uz.aim.marketshop.utils.jwt.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.aim.marketshop.config.security.SecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

public class JWTFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final AuthUserService authUserService;

    public JWTFilter(TokenService tokenService,
                     AuthUserService authUserService) {
        this.tokenService = tokenService;
        this.authUserService = authUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isOpenUrl.apply(request.getRequestURI())) {
            try {
                String token = parseJwt(request);
                if (tokenService.isValid(token)) {
                    String username = tokenService.getSubject(token);
                    UserDetails userDetails = authUserService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private final static Function<String, Boolean> isOpenUrl = (url) -> Arrays.stream(SecurityConstants.WHITE_LIST).anyMatch(s -> s.startsWith(url));
}

