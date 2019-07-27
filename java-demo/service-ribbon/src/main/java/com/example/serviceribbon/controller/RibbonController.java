package com.example.serviceribbon.controller;

import com.example.serviceribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/ribbon/hi")
    public String ribbonFromHi() {
        return helloService.hiService();
    }
}