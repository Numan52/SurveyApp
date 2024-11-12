package com.example.surveyapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/")
    public String getHome() {
        return "dashboard";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("forms/create")
    public String getCreate() {
        return "create-form";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @GetMapping("/take-survey")
    public String getTakeSurvey() {
        return "take-survey";
    }

    @GetMapping("/thanks-for-participating")
    public String getThanks() {
        return "thanks";
    }
}
