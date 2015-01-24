package net.devopssolutions.microservice.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/")

    public ModelAndView login() {
        return new ModelAndView("home/home");
    }
}
