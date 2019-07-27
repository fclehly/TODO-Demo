package com.example.servicefeign.feign;

import org.springframework.stereotype.Component;

@Component
public class serviceHiClientHystrix implements serviceHiClient {
    @Override
    public String sayHi(String name) {
        return "fail call hi : " + name;
    }
}
