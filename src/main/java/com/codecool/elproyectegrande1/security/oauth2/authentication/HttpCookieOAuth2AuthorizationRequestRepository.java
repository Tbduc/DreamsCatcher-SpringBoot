package com.codecool.elproyectegrande1.security.oauth2.authentication;

import com.codecool.elproyectegrande1.util.CookieUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest>{
    public static final String OAUTH2_AUTH_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_COOKIE_NAME = "redirect_uri";
    public static final int COOKIE_EXPIRE_SEC = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OAUTH2_AUTH_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if(authorizationRequest == null){
            removeCookies(request, response);
            return;
        }

        response.addCookie(new Cookie("cookie", "padu"));

        CookieUtils.addCookie(response, OAUTH2_AUTH_REQUEST_COOKIE_NAME, CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SEC);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_COOKIE_NAME);
        if (redirectUriAfterLogin != null && !redirectUriAfterLogin.equals("")){
            CookieUtils.addCookie(response, REDIRECT_URI_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SEC);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeCookies(HttpServletRequest request, HttpServletResponse response){
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTH_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_COOKIE_NAME);
    }
}
