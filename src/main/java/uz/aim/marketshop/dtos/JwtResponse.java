package uz.aim.marketshop.dtos;

public record JwtResponse(
        String accessToken,
        String refreshToken,
        String tokenType) {
}
