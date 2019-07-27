package com.example.servicefeign.controller;

import com.example.servicefeign.feign.serviceHiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    private serviceHiClient serviceHiClient;

    @GetMapping
    public String hi(@RequestParam("name") String name) {
        return serviceHiClient.sayHi(name);
    }
}
