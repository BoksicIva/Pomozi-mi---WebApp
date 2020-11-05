package NULL.DTPomoziMi.security;

import NULL.DTPomoziMi.jwt.JwtUtil;
import NULL.DTPomoziMi.properties.JwtConstants;
import NULL.DTPomoziMi.service.TokenService;
import NULL.DTPomoziMi.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutHandler implements LogoutSuccessHandler {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String token = CookieUtil.getValue(request, JwtConstants.JWT_COOKIE_NAME);

            String username = jwtUtil.extractUsername(token);

            tokenService.updateToken(username, null);
            
            CookieUtil.clear(response, JwtConstants.JWT_COOKIE_NAME);
            CookieUtil.clear(response, JwtConstants.JWT_REFRESH_COOKIE_NAME);
    }
}
