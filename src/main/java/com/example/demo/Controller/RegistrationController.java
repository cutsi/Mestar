package com.example.demo.Controller;

import com.example.demo.Models.AppUser;
import com.example.demo.Repos.AppUserRepository;
import com.example.demo.Services.AppUserService;
import com.example.demo.Requests.RegistrationRequest;
import com.example.demo.Services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final AppUserService appUserService;
    private final RegistrationService registrationService;
    private final AppUserRepository userRepository;
    @PostMapping
    public String register(@ModelAttribute RegistrationRequest request, Model model,
                           @RequestParam("password1") String pass) {
        System.out.println(request.getFirstName() + request.getEmail() + request.getLastName() + request.getPassword() + request.getAddress() + request.getPhone());
        System.out.println("PHONE NUMBER: " + pass);
        request.setPassword(pass);
        registrationService.register(request);
        Optional<AppUser> user = userRepository.findByEmail(request.getFirstName());
        AppUser usr = (AppUser) appUserService.loadUserByUsername(request.getFirstName());

        model.addAttribute("user", usr);
        return "welcome";
    }
    @GetMapping
    public String registered(){
        return "home";
    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "/sign-up")
    public String signup(@ModelAttribute RegistrationRequest registrationRequest,
                         Model model){
        model.addAttribute("user", new AppUser());
        return "sign-up";
    }
    @GetMapping(path = "[**")
    public String confirmed_token(){
        return "success";
    }

    @PostMapping(path = "/sign-up")
    public String signup_post(){
        return "home";
    }
    @PostMapping(path = "/custom-logout")
    public String customLogout(){
        return "custom-logout";
    }
    @PostMapping(path = "/logout-success")
    public String LogoutSuccess(){
        return "logout-success";
    }
}
