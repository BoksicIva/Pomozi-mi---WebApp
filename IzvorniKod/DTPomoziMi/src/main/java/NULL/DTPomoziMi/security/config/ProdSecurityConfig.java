package NULL.DTPomoziMi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import NULL.DTPomoziMi.web.filters.JwtRequestFilter;

@Profile("prod")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProdSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private LogoutSuccessHandler myLogoutHandler;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.cors()
			.and()
			.csrf()
			.csrfTokenRepository(cookieCsrfTokenRepository());

		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/getCsrf")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/api/auth/*")
			.permitAll()
			.anyRequest()
			.authenticated();

		http
			.formLogin()
			.disable()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler(myLogoutHandler);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	CookieCsrfTokenRepository cookieCsrfTokenRepository() {
		CookieCsrfTokenRepository repo = new CookieCsrfTokenRepository();
		repo.setCookieHttpOnly(false);
		repo.setCookieName("X-CSRF-COOKIE"); //TODO constants
		repo.setHeaderName("X-CSRF-TOKEN");

		return repo;
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/");
		web.ignoring().antMatchers("/*.ico");
		web.ignoring().antMatchers("/*.js");
		web.ignoring().antMatchers("/*.json");
		web.ignoring().antMatchers("/*.png");
		web.ignoring().antMatchers("/static/**");
		web.ignoring().antMatchers("/register");
		web.ignoring().antMatchers("/login");
		web.ignoring().antMatchers("/home");
	}

	@Bean
	public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(11); }

}
