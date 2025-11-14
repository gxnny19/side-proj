package com.jgg.side_proj.controller;

import com.jgg.side_proj.model.OnbidItem;
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
        // 1. API에서 데이터 가져와서 DB에 저장
        int savedCount = onbidService.saveItems(sido);
        
        // 2. 해당 지역 데이터만 DB에서 조회
        List<OnbidItem> items = onbidService.getItemsBySido(sido);
        
        model.addAttribute("sido", sido);
        model.addAttribute("savedCount", savedCount);
        model.addAttribute("items", items);
        model.addAttribute("totalCount", items.size());
        
        return "data";
    }
}