package net.devopssolutions.microservice.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/login-error")
    public ModelAndView loginError() {
        return new ModelAndView("login").addObject("loginError", true);
    }
}
