package com.aquamarine.barraiser.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthenticationController {
    @GetMapping(path = "/basicauth")
    public AuthenticationBean authenticate() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");
        return new AuthenticationBean("You are authenticated");
    }
}

class AuthenticationBean {
    private String message;
    public AuthenticationBean(String str) {
        this.message = str;
    }
}
