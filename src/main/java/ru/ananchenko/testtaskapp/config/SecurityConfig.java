package ru.ananchenko.testtaskapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.ananchenko.testtaskapp.security.JwtAuthenticationEntryPoint;
import ru.ananchenko.testtaskapp.security.jwt.JwtConfigurer;
import ru.ananchenko.testtaskapp.security.jwt.JwtTokenProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;

    private static final String USER_ENDPOINT = "/profiles/**";
    private static final String ERROR_ENDPOINT = "/error/last";

    @Autowired
    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            JwtAuthenticationEntryPoint authenticationErrorHandler
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationErrorHandler = authenticationErrorHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/exit",
                        "/exit-success",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-ui.html**", "/swagger-resources/**",
                        "/v2/api-docs**", "/webjars/**"
                )
                .permitAll()
                .antMatchers(USER_ENDPOINT, ERROR_ENDPOINT).hasRole("DEFAULT_USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/common/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                "/configuration/security", "/swagger-ui/", "/webjars/**");
    }
}
