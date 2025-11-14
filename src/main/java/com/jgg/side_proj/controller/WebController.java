package com.jgg.side_proj.controller;

import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.service.OnbidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final OnbidService onbidService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/data")
    public String showData(@RequestParam(defaultValue = "경상북도") String sido, Model model) {
        int savedCount = onbidService.saveItems(sido);
        List<OnbidEntity> items = onbidService.getAllItems();
        
        model.addAttribute("sido", sido);
        model.addAttribute("savedCount", savedCount);
        model.addAttribute("items", items);
        
        return "data";
    }
}