package com.jgg.side_proj.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    
    private final AccessLogService accessLogService;
    
    public DataService(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }
    
    public List<Map<String, String>> getDataBySido(String sido) {
        accessLogService.logAccess("사용자가 " + sido + " 데이터를 조회했습니다");
        
        List<Map<String, String>> data = new ArrayList<>();
        
        // 샘플 데이터 생성
        for (int i = 1; i <= 5; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("id", String.valueOf(i));
            item.put("cltrMnmtNo", "2024" + String.format("%03d", i));
            item.put("cltrNm", sido + " 공매물건 " + i + "호");
            item.put("sido", sido);
            item.put("dtlAddr", sido + " 지역 상세주소 " + i);
            item.put("goodsPrice", String.valueOf((i * 100000000L)));
            item.put("createdAt", LocalDateTime.now().minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            data.add(item);
        }
        
        return data;
    }
}