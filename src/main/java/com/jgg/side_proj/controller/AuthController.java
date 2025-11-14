package com.jgg.side_proj.controller;

import com.jgg.side_proj.service.AccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AccessLogService accessLogService;

    @GetMapping("/login")
    public String login() {
        accessLogService.logAccess("사용자가 로그인 페이지에 접근했습니다");
        return "login";
    }
}