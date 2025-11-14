package com.jgg.connproj.service;

import com.jgg.connproj.dto.KamcoItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;

@Service
public class KamcoApiService {

    private static final Logger logger = LoggerFactory.getLogger(KamcoApiService.class);
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${kamco.api.servicekey:}")
    private String serviceKey;

    public List<KamcoItemDto> getKamcoData() {
        return searchByRegionAndCategory("경기도", "realestate");
    }

    public List<KamcoItemDto> searchByRegionAndCategory(String region, String category) {
        // 서비스키 확인
        if (serviceKey == null || serviceKey.isEmpty() || "YOUR_SERVICE_KEY_HERE".equals(serviceKey)) {
            logger.error("KAMCO API 서비스키가 설정되지 않았습니다.");
            return createSampleData(region, category);
        }

        String categoryCode = getCategoryCode(category);
        String encodedRegion = java.net.URLEncoder.encode(region, java.nio.charset.StandardCharsets.UTF_8);
        
        String url = "http://openapi.onbid.co.kr/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList" +
                "?serviceKey=" + serviceKey +
                "&numOfRows=20&pageNo=1&DPSL_MTD_CD=0001" + categoryCode +
                "&SIDO=" + encodedRegion;
        
        logger.info("Category: {}, Region: {}, URL: {}", category, region, url);
        
        try {
            String response = restTemplate.getForObject(url, String.class);
            logger.info("응답 데이터: {}", response);
            
            if (response.contains("SERVICE ACCESS DENIED ERROR") || !response.contains("<item>")) {
                return createSampleData(region, category);
            }
            
            return parseXmlToDto(response, category);
        } catch (Exception e) {
            logger.error("API 호출 오류: {}", e.getMessage());
            return createSampleData(region, category);
        }
    }
    
    private List<KamcoItemDto> parseXmlToDto(String xmlData, String category) {
        List<KamcoItemDto> items = new ArrayList<>();
        // XML 파싱 로직 추가 예정
        return items;
    }
    
    private List<KamcoItemDto> createSampleData(String region, String category) {
        List<KamcoItemDto> items = new ArrayList<>();
        
        if ("realestate".equals(category)) {
            items.add(createItem("2024110100001", region + " 아파트 102동 1501호", "500000000", "일반매각", region, region + " 강남구 역삼동 123-45", "2024-01-15", "부동산"));
            items.add(createItem("2024110100002", region + " 상가건물 1층", "300000000", "일반매각", region, region + " 서초구 서초동 567-89", "2024-01-20", "부동산"));
            items.add(createItem("2024110100003", region + " 단독주택", "800000000", "일반매각", region, region + " 마포구 연희동 234-56", "2024-01-25", "부동산"));
        } else if ("movable".equals(category)) {
            items.add(createItem("2024110200001", "현대 소나타 2.0 디젤", "15000000", "일반매각", region, region + " 중고차 매매사업소", "2024-01-18", "동산"));
            items.add(createItem("2024110200002", "CNC 가공기계", "45000000", "일반매각", region, region + " 제조업체 공장", "2024-01-28", "동산"));
        } else if ("rights".equals(category)) {
            items.add(createItem("2024110300001", "스마트폰 충전기술 특허권", "120000000", "일반매각", region, region + " 지역 특허청", "2024-02-05", "권리"));
            items.add(createItem("2024110300002", "카페 브랜드 상표권", "25000000", "일반매각", region, region + " 상표권 등록청", "2024-02-10", "권리"));
        }
        
        return items;
    }
    
    private KamcoItemDto createItem(String thingNo, String thingNm, String apslAsesAmt, String dpslMtdNm, String sidoNm, String addr, String bidDt, String category) {
        KamcoItemDto item = new KamcoItemDto();
        item.setThingNo(thingNo);
        item.setThingNm(thingNm);
        item.setApslAsesAmt(apslAsesAmt);
        item.setDpslMtdNm(dpslMtdNm);
        item.setSidoNm(sidoNm);
        item.setAddr(addr);
        item.setBidDt(bidDt);
        item.setCategory(category);
        return item;
    }

    private String getCategoryCode(String category) {
        switch (category) {
            case "realestate": return "&CTGR_HIRK_ID=10000&CTGR_HIRK_ID_MID=10100";
            case "movable": return "&CTGR_HIRK_ID=20000&CTGR_HIRK_ID_MID=20100";
            case "rights": return "&CTGR_HIRK_ID=30000&CTGR_HIRK_ID_MID=30100";
            default: return "&CTGR_HIRK_ID=10000&CTGR_HIRK_ID_MID=10100";
        }
    }
}