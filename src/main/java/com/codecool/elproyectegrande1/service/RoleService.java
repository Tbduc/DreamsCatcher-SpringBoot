package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.entity.ERole;
import com.codecool.elproyectegrande1.entity.Role;
import com.codecool.elproyectegrande1.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final String DREAMER = "dreamer";
    private final String MENTOR = "mentor";
    private final String ADMIN = "admin";

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void findRoleAndAddToRoles(Set<String> strRoles, Set<Role> roles) {
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_DREAMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case MENTOR:
                        Role mentorRole = roleRepository.findByName(ERole.ROLE_MENTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(mentorRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_DREAMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
    }
}
