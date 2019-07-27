package com.example.servicehi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("/hi")
    public String hi(
            @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return "{\n" +
                "\t\"message\": \"hi : " + name + " from "+ serviceName +":" + port + "\"" +
                "\n}";
    }
}
