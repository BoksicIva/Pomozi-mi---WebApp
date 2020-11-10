package NULL.DTPomoziMi.web.filters;

import NULL.DTPomoziMi.jwt.JwtUtil;
import NULL.DTPomoziMi.properties.JwtConstants;
import NULL.DTPomoziMi.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String token = CookieUtil.getValue(request, JwtConstants.JWT_COOKIE_NAME);
            String refreshToken = CookieUtil.getValue(request, JwtConstants.JWT_REFRESH_COOKIE_NAME);

            String emailFromToken = null;
            String emailFromRefreshToken = null;

            if(token != null && !token.isBlank())
                emailFromToken = jwtUtil.extractUsername(token);
            if(refreshToken != null && !refreshToken.isBlank())
                emailFromRefreshToken = jwtUtil.extractUsername(refreshToken);

            if(emailFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(token)) {
                    setAuth(token);
                }

            }else if(emailFromRefreshToken != null && SecurityContextHolder.getContext().getAuthentication() == null){
                if(jwtUtil.validateRefreshToken(emailFromRefreshToken)){
                    setAuth(refreshToken);

                    Map<String, Object> claims = new HashMap<>();
                    claims.put("role", jwtUtil.extractRole(refreshToken));

                    String newtoken = jwtUtil.generateToken(claims, emailFromRefreshToken);
                    CookieUtil.create(response, JwtConstants.JWT_COOKIE_NAME, newtoken, false, -1);
                }else{
                    CookieUtil.clear(response, JwtConstants.JWT_REFRESH_COOKIE_NAME);
                    CookieUtil.clear(response, JwtConstants.JWT_COOKIE_NAME);
                }
            }else if(token != null && refreshToken != null){
                CookieUtil.clear(response, JwtConstants.JWT_REFRESH_COOKIE_NAME);
                CookieUtil.clear(response, JwtConstants.JWT_COOKIE_NAME);
            }

        filterChain.doFilter(request, response);
    }

    private void setAuth(String token){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                jwtUtil.extractUsername(token),
                null,
                Arrays.asList(new SimpleGrantedAuthority(jwtUtil.extractRole(token)))
        );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
