package net.devopssolutions.microservice.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("auth/login");
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("auth/logout");
    }
}
