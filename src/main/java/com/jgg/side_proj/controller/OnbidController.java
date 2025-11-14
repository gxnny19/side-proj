package com.jgg.side_proj.controller;

import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.service.OnbidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onbid")
@RequiredArgsConstructor
public class OnbidController {

    private final OnbidService service;

    @GetMapping("/save")
    public String save(@RequestParam String sido) {
        int cnt = service.saveItems(sido);
        return cnt + "개 저장 완료!";
    }
    
    @GetMapping("/items")
    public List<OnbidEntity> getItems() {
        return service.getAllItems();
    }
    
    @PostMapping("/fetch")
    public String fetchAndSave() {
        service.fetchAndSaveOnbidData();
        return "Data fetch completed";
    }
}
