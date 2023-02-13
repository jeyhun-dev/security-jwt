package az.cmammad.security.security.jwt;

import az.cmammad.security.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final HttpServletRequest httpRequest;

    @Value("${spring.secret-key}")
    private String secret;

    @Value("${spring.jwt-issuer}")
    private String issuer;

    public String generateAccessToken(final UserEntity user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("user_id", String.valueOf(user.getId()));
        claims.put("phone_number", user.getPhoneNumber());
        claims.put("address", user.getAddress());
        claims.put("created_at", user.getCreatedDate().toString());
        claims.put("full_name", user.getFirstname() + " " + user.getLastname());
        return createToken(claims, user.getEmail());
    }

    private String createToken(final Map<String, String> claims,
                               final String subject) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 + 60 * 12)) //expire time bax
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setIssuer(issuer)
                .setAudience(httpRequest.getRemoteHost())
                .compact();
    }

    public boolean validateToken(@NotNull final String token,
                                 @NotNull final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpireToken(token));
    }

    private boolean isExpireToken(@NotNull final String token) {
        final var userExpireDate = extractClaim(token, Claims::getExpiration);
        return userExpireDate.before(new Date());
    }

    //return edeceyik login zamani, token ile birlikde
    public Date getCreateDateTimeForJwt(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(final String token,
                               final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        final byte[] keyByte = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
