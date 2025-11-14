package com.jgg.side_proj.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccessLogService {
    
    private final List<String> accessLogs = new ArrayList<>();
    
    public void logAccess(String action) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = timestamp + " - " + action;
        accessLogs.add(0, logEntry); // 최신 로그를 맨 위에
        
        // 최대 50개까지만 유지
        if (accessLogs.size() > 50) {
            accessLogs.remove(accessLogs.size() - 1);
        }
    }
    
    public List<String> getAccessLogs() {
        return new ArrayList<>(accessLogs);
    }
}