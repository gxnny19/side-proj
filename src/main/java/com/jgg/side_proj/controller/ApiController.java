package com.jgg.side_proj.controller;

import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.service.KamcoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private KamcoApiService kamcoApiService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }
    
    @GetMapping("/dto-test")
    public List<KamcoItemDto> getDtoTest() {
        return kamcoApiService.searchByRegionAndCategory("경기도", "realestate");
    }

    @GetMapping("/kamco")
    public List<KamcoItemDto> getKamcoData() {
        try {
            return kamcoApiService.getKamcoData();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @GetMapping("/search/{category}")
    public List<KamcoItemDto> searchByCategory(@PathVariable String category, @RequestParam String region) {
        try {
            return kamcoApiService.searchByRegionAndCategory(region, category);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}