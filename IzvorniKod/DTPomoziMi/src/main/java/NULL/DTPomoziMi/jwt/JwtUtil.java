package NULL.DTPomoziMi.jwt;

import NULL.DTPomoziMi.MongoRepo.JwtMongoRepository;
import NULL.DTPomoziMi.properties.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final int EXPIRATION = 1000 * 60 * 60 * 2; // 2h

    @Autowired
    private JwtMongoRepository jwtMongoRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // payload ... za sada prazno... moze ic sta god hoces spremit
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS384, Constants.SECRET_KEY).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String savedToken = null;
        try{
           savedToken = jwtMongoRepository.findById(userDetails.getUsername()).get().getToken();
        }catch (NoSuchElementException e){}

        if(!token.equals(savedToken))
            return false;

        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
