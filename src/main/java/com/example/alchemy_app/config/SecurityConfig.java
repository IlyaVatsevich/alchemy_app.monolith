package com.example.alchemy_app.config;

import com.example.alchemy_app.enums.UserRole;
import com.example.alchemy_app.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final JwtFilter filter;

    private static final String USER_AUTHORITY = UserRole.USER.getAuthority();
    private static final String ADMIN_AUTHORITY = UserRole.ADMIN.getAuthority();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http = http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                ex.getMessage()
                        )
                )
                .and();

        http.authorizeRequests()
                .antMatchers(
                        "/api/v1/user/registration",
                        "/api/v1/user/login",
                        "/api/v1/user/refresh_token").anonymous()
                .antMatchers("/api/v1/user/**").hasAnyAuthority(USER_AUTHORITY,ADMIN_AUTHORITY)
                .antMatchers("/api/v1/ingredient/**").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers("/api/v1/action/**").hasAnyAuthority(USER_AUTHORITY,ADMIN_AUTHORITY);

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}