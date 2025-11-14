package com.jgg.side_proj.controller;

import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.service.OnbidEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entity")
@RequiredArgsConstructor
public class OnbidEntityController {

    private final OnbidEntityService onbidEntityService;

    @GetMapping("/test")
    public String test() {
        return "OnbidEntityController is working!";
    }

    @GetMapping("/save")
    public String save(@RequestParam String sido) {
        int cnt = onbidEntityService.saveEntities(sido);
        return cnt + "개 엔티티 저장 완료!";
    }
    
    @GetMapping("/items")
    public List<OnbidEntity> getItems() {
        return onbidEntityService.getAllEntities();
    }
    
    @GetMapping("/search")
    public List<OnbidEntity> searchBySido(@RequestParam String sido) {
        onbidEntityService.saveEntities(sido);
        return onbidEntityService.getEntitiesBySido(sido);
    }
}