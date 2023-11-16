package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {
    private final UserRepository userRepository;

    public ParameterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
