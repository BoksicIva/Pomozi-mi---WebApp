package NULL.DTPomoziMi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*").allowedOrigins("http://localhost:3000").allowedHeaders("*")
        .allowedMethods(HttpMethod.GET.toString(),
                HttpMethod.POST.toString(), 
                HttpMethod.OPTIONS.toString(),
                HttpMethod.PATCH.toString(),
                HttpMethod.PUT.toString(),
                HttpMethod.DELETE.toString())
        .allowCredentials(true);
    }

}
