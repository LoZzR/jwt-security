package com.jwt.security.config;

import com.jwt.security.filters.InitialAuthenticationFilter;
import com.jwt.security.filters.JwtAuthenticationFilter;
import com.jwt.security.providers.OtpAuthenticationProvider;
import com.jwt.security.providers.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private InitialAuthenticationFilter initialAuthenticationFilter;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private OtpAuthenticationProvider otpAuthenticationProvider;

    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(usernamePasswordAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(
                initialAuthenticationFilter,
                BasicAuthenticationFilter.class)
            .addFilterAfter(
                jwtAuthenticationFilter,
                BasicAuthenticationFilter.class
            );

        http.authorizeRequests()
                .anyRequest().authenticated();
    }

    /*Adds the AuthenticationManager
     to the Spring context so that we can
     autowire it from the filter class*/
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void setInitialAuthenticationFilter(InitialAuthenticationFilter initialAuthenticationFilter) {
        this.initialAuthenticationFilter = initialAuthenticationFilter;
    }

    @Autowired
    public void setJwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Autowired
    public void setOtpAuthenticationProvider(OtpAuthenticationProvider otpAuthenticationProvider) {
        this.otpAuthenticationProvider = otpAuthenticationProvider;
    }

    @Autowired
    public void setUsernamePasswordAuthenticationProvider(UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) {
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
    }
}
