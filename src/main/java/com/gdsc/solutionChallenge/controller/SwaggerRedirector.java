package com.gdsc.solutionChallenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apidocs")
public class SwaggerRedirector {
    @GetMapping
    public String redirect() {
        return "redirect:/swagger-ui/index.html";
    }
}
