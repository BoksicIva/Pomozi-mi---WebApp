package NULL.DTPomoziMi.jwt;

import NULL.DTPomoziMi.DAO.UserDAO;
import NULL.DTPomoziMi.properties.JwtConstants;
import NULL.DTPomoziMi.service.TokenService;
import NULL.DTPomoziMi.util.MyCollectors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TokenService tokenService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractRole(String token) { return extractClaim(token, claims -> claims.get("role", String.class));}

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JwtConstants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",
                    userDetails.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(MyCollectors.toSingleton()
                ));
        return createToken(claims, userDetails.getUsername(), JwtConstants.JWT_EXPIRATION);
    }

    public String generateToken(Map<String, Object> claims, String username) {
        return createToken(claims, username, JwtConstants.JWT_EXPIRATION);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // payload ... za sada prazno... moze ic sta god hoces spremit
        return createToken(claims, userDetails.getUsername(), JwtConstants.JWT_REFRESH_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS384, JwtConstants.SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        String username = extractUsername(token);
        return !isTokenExpired(token);
    }

    public boolean validateRefreshToken(String token) {
        String username = extractUsername(token);
        return (!isTokenExpired(token) && token.equals(tokenService.getTokenByEmail(username)));
    }

}
