package com.aquamarine.barraiser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/api/test")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }
}
