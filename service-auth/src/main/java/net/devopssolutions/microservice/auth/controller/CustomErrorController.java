package net.devopssolutions.microservice.auth.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ModelAndView error(HttpServletRequest request) {
        Object errorCode = request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = null;
        String errorTrace = null;
        if (throwable != null) {
            errorMessage = throwable.getMessage();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            errorTrace = sw.toString();
        }

        return new ModelAndView("error/error")
                .addObject("errorCode", errorCode)
                .addObject("errorMessage", errorMessage)
                .addObject("errorTrace", errorTrace);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
