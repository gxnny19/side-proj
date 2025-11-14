package com.jgg.side_proj.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onbid")
public class OnbidController {

    @GetMapping("/test")
    public String test() {
        return "OnbidController is working!";
    }
}