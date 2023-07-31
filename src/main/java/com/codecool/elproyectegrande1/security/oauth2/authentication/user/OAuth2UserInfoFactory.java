package com.codecool.elproyectegrande1.security.oauth2.authentication.user;

import com.codecool.elproyectegrande1.entity.AuthProvider;
import com.codecool.elproyectegrande1.service.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo get(String registrationId, Map<String, Object> attributes){
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " is not supported");
        }
    }
}