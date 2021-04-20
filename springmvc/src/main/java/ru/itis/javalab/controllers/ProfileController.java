package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.javalab.dto.UserSignInForm;
import ru.itis.javalab.security.details.UserDetailsImpl;
import ru.itis.javalab.services.UsersService;

@Controller
public class ProfileController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getSignInPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("email", userDetails.getUsername());
        return "profile_page";
    }
}
