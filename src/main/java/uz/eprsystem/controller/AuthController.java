package uz.eprsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.eprsystem.entity.dto.AuthDto;
import uz.eprsystem.entity.dto.JwtResponse;
import uz.eprsystem.entity.dto.UserRequestDto;
import uz.eprsystem.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserRequestDto user){
        return userService.save(user);
    }


    @PostMapping("/sign-in")
    public JwtResponse signUp(@RequestBody AuthDto authDto){
        return userService.signIn(authDto);
    }

}
