package com.budget_app.controllers;

import com.budget_app.dto.ProfileDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {

    @GetMapping
    public ProfileDto getCurrentUserInfo(Principal principal) {
        //User user = userService.findByUsername(principal.getName());
        return new ProfileDto(principal.getName());
    }
}
