package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.javalab.services.SignUpService;

@Controller
public class ConfirmController {

    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/confirm/{code}", method = RequestMethod.GET)
    public String confirm(@PathVariable("code") String code, Model model) {
        signUpService.confirmUserWithCode(code);
        return "success_page";
    }
}
