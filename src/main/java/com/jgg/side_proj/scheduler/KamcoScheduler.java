package com.jgg.side_proj.scheduler;

import com.jgg.side_proj.service.OnbidService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KamcoScheduler {

    private static final Logger logger = LoggerFactory.getLogger(KamcoScheduler.class);
    
    private final OnbidService onbidService;

    @Scheduled(fixedDelay = 600000) // 10분마다
    public void fetchKamcoData() {
        logger.info("스케줄링 시작: 캠코 데이터 수집");
        
        String[] regions = {"경상북도", "서울특별시", "경기도"};
        
        for (String region : regions) {
            try {
                int count = onbidService.saveItems(region);
                logger.info("{} 지역 데이터 {}개 저장 완료", region, count);
            } catch (Exception e) {
                logger.error("{} 지역 데이터 수집 실패: {}", region, e.getMessage());
            }
        }
        
        logger.info("스케줄링 완료");
    }
}