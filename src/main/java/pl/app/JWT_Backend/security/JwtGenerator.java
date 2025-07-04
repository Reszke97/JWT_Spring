package pl.app.JWT_Backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.app.JWT_Backend.user.enums.TokenType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import pl.app.JWT_Backend.user.dto.AuthResponseDto;
import pl.app.JWT_Backend.user.services.CustomUserDetailsService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessTokenExpirationMs}")
    private Long accessTokenExpirationMs;

    @Value("${jwt.refreshTokenExpirationMs}")
    private Long refreshTokenExpirationMs;

    @Getter
    private SecretKey key;

    private static final String VALID_TOKENS_PREFIX = "valid_token:";

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    public String generateToken(String username, List<Long> permissionGroupIds, Long expirationMs, TokenType tokenType) {
        Instant now = Instant.now();

        String token = Jwts
                .builder()
                .subject(username)
                .claim("permissionGroups", permissionGroupIds) // Only add permission groups
                .claim("token_type", tokenType)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(key, Jwts.SIG.HS512)
                .compact();

        // Store the token in Redis
        redisTemplate.opsForValue().set(VALID_TOKENS_PREFIX + token, username, expirationMs, TimeUnit.MILLISECONDS);

        return token;
    }

    public String generateAccessToken(String username, List<Long> permissionGroups) {
        return generateToken(username, permissionGroups, accessTokenExpirationMs, TokenType.ACCESS);
    }

    public String generateRefreshToken(String username, List<Long> permissionGroups) {
        return generateToken(username, permissionGroups, refreshTokenExpirationMs, TokenType.REFRESH);
    }

    public AuthResponseDto generateTokens(String username, List<Long> permissionGroups) {
        String accessToken = generateAccessToken(username, permissionGroups);
        String refreshToken = generateRefreshToken(username, permissionGroups);
        return new AuthResponseDto(accessToken, refreshToken);
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public List<Long> extractPermissionGroups(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        List<?> rawList = claims.get("permissionGroups", List.class);
        return rawList
                .stream()
                .filter(item -> item instanceof Number)
                .map(item -> ((Number) item).longValue())
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String requestUrl = request.getRequestURL().toString();
            if (redisTemplate != null && Boolean.TRUE.equals(redisTemplate.hasKey(VALID_TOKENS_PREFIX + token))) {
                Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
                if (
                        (TokenType.REFRESH.name().equals(claims.get("token_type")) && !requestUrl.contains("api/v1/auth/refresh"))
                                || (TokenType.ACCESS.name().equals(claims.get("token_type")) && requestUrl.contains("api/v1/auth/refresh"))
                ) {
                    throw new JwtException("Invalid token type");
                }
                return true;
            }
            throw new JwtException("JWT signature does not match or token is malformed");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage(), e.fillInStackTrace());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }

    public void invalidateToken(String token) {
        redisTemplate.delete(VALID_TOKENS_PREFIX + token);
    }
}
