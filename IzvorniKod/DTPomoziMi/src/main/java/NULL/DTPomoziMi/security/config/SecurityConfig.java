package NULL.DTPomoziMi.security.config;

import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.web.filters.CsrfTokenRequestFilter;
import NULL.DTPomoziMi.web.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CsrfTokenRequestFilter csrfTokenRequestFilter;

    @Autowired
    private LogoutSuccessHandler myLogoutHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().csrfTokenRepository(cookieCsrfTokenRepository()).ignoringAntMatchers("/h2/**") // TODO makni h2
                .and()
                .authorizeRequests()
                .antMatchers("/h2/**").permitAll() // TODO makni h2
                .antMatchers("/auth/*").permitAll()
                .antMatchers("/getCsrf").permitAll()
                .anyRequest().hasAnyRole(Role.ROLE_USER.toString().substring(5), Role.ROLE_ADMIN.toString().substring(5))
                .and()
                .formLogin().disable()
                .logout().logoutUrl("/logout")
                    .logoutSuccessHandler(myLogoutHandler);


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(csrfTokenRequestFilter, CsrfFilter.class);
        http.headers().frameOptions().disable(); // TODO h2 makni

    }

    CookieCsrfTokenRepository cookieCsrfTokenRepository(){
        CookieCsrfTokenRepository repo = new CookieCsrfTokenRepository();
        repo.setCookieHttpOnly(true);
        repo.setCookieName("X-CSRF-COOKIE"); //TODO constants
        repo.setHeaderName("X-CSRF-TOKEN");

        return repo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    } 
    
}
