package com.jgg.side_proj.controller;

import com.jgg.side_proj.model.OnbidResponse;
import com.jgg.side_proj.service.OnbidService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnbidController {

    private final OnbidService onbidService;

    public OnbidController(OnbidService onbidService) {
        this.onbidService = onbidService;
    }

    @GetMapping("/search")
    public OnbidResponse searchBySido(@RequestParam String sido) {
        return onbidService.searchBySido(sido);
    }
}