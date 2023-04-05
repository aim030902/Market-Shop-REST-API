package uz.aim.marketshop.config.security;


public class SecurityConstants {
    public static final String[] WHITE_LIST = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/product/get/**",
            "/api/v1/product/**",
            "/api/v1/category/getAll",
            "/api/v1/home",
            "/api/v1/search",
            "/swagger-ui/**",
            "/api-docs/**",
            "/image"
    };
}
