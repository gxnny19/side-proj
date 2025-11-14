package com.jgg.connproj.controller;

import com.jgg.connproj.dto.KamcoItemDto;
import com.jgg.connproj.service.KamcoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Controller
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    
    @Autowired
    private KamcoApiService kamcoApiService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/search/{category}")
    public String search(@PathVariable String category, Model model) {
        model.addAttribute("category", category);
        return "search";
    }
}