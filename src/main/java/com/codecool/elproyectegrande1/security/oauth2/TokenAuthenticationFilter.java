package com.codecool.elproyectegrande1.security.oauth2;

import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.service.UserDetailsServiceImpl;
import com.codecool.elproyectegrande1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                String userId = tokenProvider.getUserIdFromToken(jwt);
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userService.loadUserById(userId);
                if(userDetails == null) {
                    userDetails = userDetailsService.loadUserByUsername(username);
                }
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Could not authenticate user", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if(token.startsWith(BEARER)){
            return token.substring(7);
        }
        return "";
    }
}
