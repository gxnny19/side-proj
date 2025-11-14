package com.jgg.side_proj.controller;

import com.jgg.side_proj.model.OnbidItem;
import com.jgg.side_proj.service.OnbidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onbid")
@RequiredArgsConstructor
public class OnbidController {

    private final OnbidService onbidService;

    @GetMapping("/test")
    public String test() {
        return "OnbidController is working!";
    }

    @GetMapping("/save")
    public String save(@RequestParam String sido) {
        int cnt = onbidService.saveItems(sido);
        return cnt + "개 저장 완료!";
    }
    
    @GetMapping("/items")
    public List<OnbidItem> getItems() {
        return onbidService.getAllItems();
    }
    
    @GetMapping("/search")
    public List<OnbidItem> searchBySido(@RequestParam String sido) {
        onbidService.saveItems(sido);
        return onbidService.getItemsBySido(sido);
    }
}