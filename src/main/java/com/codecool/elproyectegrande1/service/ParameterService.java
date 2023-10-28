package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {
    private final UserRepository userRepository;

    public ParameterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public Parameter saveAndGetParameter(String param, String userId) {
//        Parameter parameter = new Parameter();
//        parameter.setParameter(param);
//        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
//        parameter.setUser(user);
//        user.addParameters(parameter);
//        userRepository.update(user, user.getId());
//        return
//    }
}
