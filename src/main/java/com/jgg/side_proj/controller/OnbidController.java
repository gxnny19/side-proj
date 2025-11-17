package com.jgg.side_proj.controller;

import com.jgg.side_proj.dto.KamcoApiResponse;
import com.jgg.side_proj.dto.SearchRequestDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.service.OnbidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OnbidController {

    private final OnbidService onbidService;

    public OnbidController(OnbidService onbidService) {
        this.onbidService = onbidService;
    }

    @GetMapping("/search")
    public KamcoApiResponse search(@RequestParam(required = false) String sido,
                                   @RequestParam(required = false) String sgk,
                                   @RequestParam(required = false) Integer numOfRows) {
        SearchRequestDto request = new SearchRequestDto();
        request.setSido(sido);
        request.setSgk(sgk);
        if (numOfRows != null) {
            request.setNumOfRows(numOfRows);
        }
        return onbidService.search(request);
    }
    
    @PostMapping("/search")
    public KamcoApiResponse searchByDto(@RequestBody SearchRequestDto request) {
        return onbidService.search(request);
    }
    
    @GetMapping("/saved")
    public List<OnbidEntity> getSavedItems(@RequestParam String sido) {
        return onbidService.getSavedItems(sido);
    }
}
