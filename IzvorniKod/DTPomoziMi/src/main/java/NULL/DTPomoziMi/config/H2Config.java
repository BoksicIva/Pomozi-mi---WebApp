/*
package NULL.DTPomoziMi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
@Profile("dev") // enable access to h2 console when developing
public class H2Config extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/h2/**")
                .and()
                .authorizeRequests().antMatchers("/h2/**").permitAll()
                .and()
                .headers().frameOptions().sameOrigin();
    }
}
*/
