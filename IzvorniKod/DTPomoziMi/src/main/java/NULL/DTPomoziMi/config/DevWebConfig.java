package NULL.DTPomoziMi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("dev")
@Configuration
@EnableWebMvc
public class DevWebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedHeaders("*")
        .allowedMethods(HttpMethod.GET.toString(),
                HttpMethod.POST.toString(), 
                HttpMethod.OPTIONS.toString(),
                HttpMethod.PATCH.toString(),
                HttpMethod.PUT.toString(),
                HttpMethod.DELETE.toString())
        .allowCredentials(true);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("**/*.map", "**/*.js", "**/*.css","**/*.txt","**/*.json", "**/*.ico", "**/*.png")
//                .addResourceLocations("classpath:/static/");
//    }
}