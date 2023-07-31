package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.repository.AdminRepository;
import com.codecool.elproyectegrande1.repository.RoleRepository;
import com.codecool.elproyectegrande1.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private AdminService adminService;
    private AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    public AdminController(AdminService adminService, RoleRepository roleRepository) {
        this.adminService = adminService;
        this.roleRepository = roleRepository;
    }

    @RolesAllowed("admin")
    @PostMapping("/{mentorNickname}/approve")
    public void approveMentor(@PathVariable("mentorNickname") String mentorNickname) {
        adminService.approveMentor(mentorNickname);
    };
}
