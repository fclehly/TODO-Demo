package com.example.servicefeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hi")
public interface serviceHiClient {
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHi(@RequestParam("name") String name);
}
