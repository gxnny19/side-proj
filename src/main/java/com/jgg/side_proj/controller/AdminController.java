package com.jgg.side_proj.controller;

import com.jgg.side_proj.service.AccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AccessLogService accessLogService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        accessLogService.logAccess("관리자가 대시보드에 접근했습니다");
        
        List<String> logs = accessLogService.getAccessLogs();
        model.addAttribute("message", "관리자 대시보드에 오신 것을 환영합니다!");
        model.addAttribute("accessLogs", logs);
        model.addAttribute("totalLogs", logs.size());
        
        return "admin/dashboard";
    }
}