package NULL.DTPomoziMi.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
        @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:/config/application.properties", ignoreResourceNotFound = true),
        @PropertySource(value ="file:./application.properties", ignoreResourceNotFound = true),
        @PropertySource(value ="file:./config/*/application.properties", ignoreResourceNotFound = true),
        @PropertySource(value ="file:./config/application.properties", ignoreResourceNotFound = true)
})
public class Constants {

    public static String JWT_COOKIE_NAME;

    public static String SECRET_KEY;

    @Value("${secret.key}")
    public void setSecretKey(String secretKey) {
        if(secretKey == null)
            throw new IllegalArgumentException("secret.key property is missing from application.properties!");
        SECRET_KEY = secretKey;
    }

    @Value("${jwt.cookie.name}")
    public void setJwtCookieName(String jwtCookieName) {
        if(jwtCookieName == null)
            throw new IllegalArgumentException("jwt.cookie.name property is missing from application.properties!");
        JWT_COOKIE_NAME = jwtCookieName;
    }
}
