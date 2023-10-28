package com.codecool.elproyectegrande1.config.spring.security;

import com.codecool.elproyectegrande1.jwt.AuthEntryPointJwt;
import com.codecool.elproyectegrande1.jwt.AuthTokenFilter;
import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.security.oauth2.RestAuthenticationEntryPoint;
import com.codecool.elproyectegrande1.security.oauth2.TokenProvider;
import com.codecool.elproyectegrande1.security.oauth2.authentication.CustomOauth2UserService;
import com.codecool.elproyectegrande1.security.oauth2.authentication.HttpCookieOAuth2AuthorizationRequestRepository;
import com.codecool.elproyectegrande1.security.oauth2.authentication.OAuth2AuthenticationFailureHandler;
import com.codecool.elproyectegrande1.security.oauth2.authentication.OAuth2AuthenticationSuccessHandler;
import com.codecool.elproyectegrande1.service.UserDetailsServiceImpl;
import com.codecool.elproyectegrande1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, TokenProvider tokenProvider, UserDetailsServiceImpl userDetailsService, UserService userService) {
        return new AuthTokenFilter(jwtUtils, tokenProvider, userDetailsService, userService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoderSecure());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoderSecure() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter(JwtUtils jwtUtils, TokenProvider tokenProvider, UserDetailsServiceImpl userDetailsService, UserService userService) {
        return new AuthTokenFilter(jwtUtils, tokenProvider, userDetailsService, userService);
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChainSecured(
            HttpSecurity http,
            AuthTokenFilter authTokenFilter,
            UserDetailsServiceImpl userDetailsService,
            AuthEntryPointJwt unauthorizedHandler,
            CustomOauth2UserService customOAuth2UserService,
            OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler
    ) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/v1/auth/**", "/auth/**", "/oauth2/**").permitAll()
                .antMatchers("/api/v1/test/**").permitAll()
                .antMatchers("/api/**", "/image/**", "/update/profile-image", "/upload", "/payment/success", "/payment/cancel", "/payment/error").permitAll()
                .antMatchers(h2ConsolePath + "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                    .and()
                    .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
        http.headers().frameOptions().sameOrigin();
        http.cors();

        http.authenticationProvider(authenticationProvider(userDetailsService));

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
