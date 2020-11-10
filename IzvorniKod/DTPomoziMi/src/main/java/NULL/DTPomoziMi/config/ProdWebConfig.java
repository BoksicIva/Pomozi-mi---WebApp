package NULL.DTPomoziMi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("prod")
@Configuration
@EnableWebMvc
public class ProdWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/*.map", "**/*.js", "**/*.css","**/*.txt","**/*.json", "**/*.ico", "**/*.png")
                .addResourceLocations("classpath:/static/");
    }
}
