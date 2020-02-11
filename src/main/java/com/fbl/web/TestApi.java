package com.fbl.web;

import com.fbl.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestApi {

    @Autowired
    TestService service;

    @GetMapping("/tsetStarter")
    public String testStarter () {
        return service.getUrl();
    }

}