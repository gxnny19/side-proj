package com.jgg.side_proj.controller;

import com.jgg.side_proj.service.AccessLogService;
import com.jgg.side_proj.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final AccessLogService accessLogService;
    private final DataService dataService;

    @GetMapping("/")
    public String index() {
        accessLogService.logAccess("사용자가 홈페이지에 접근했습니다");
        return "index";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam String sido, Model model) {
        List<Map<String, String>> data = dataService.getDataBySido(sido);
        
        model.addAttribute("sido", sido);
        model.addAttribute("items", data);
        model.addAttribute("totalCount", data.size());
        
        return "search-result";
    }
}