package com.jgg.side_proj.component;

import com.jgg.side_proj.dto.KamcoApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class XmlApiClient {

    private static final Logger logger = LoggerFactory.getLogger(XmlApiClient.class);
    
    @Value("${api.camco.serviceKey}")
    private String serviceKey;
    
    private final RestTemplate restTemplate;
    
    public KamcoApiResponse callKamcoApi(String sido) {
        return callKamcoApi(sido, 100, 1);
    }
    
    public KamcoApiResponse callKamcoApi(String sido, int numOfRows, int pageNo) {
        try {
            String url = buildApiUrl(sido, numOfRows, pageNo);
            logger.info("API 호출: {}", url);
            
            KamcoApiResponse response = restTemplate.getForObject(url, KamcoApiResponse.class);
            logger.info("API 응답 성공: {} 지역", sido);
            
            return response;
        } catch (Exception e) {
            logger.error("API 호출 실패: {} - {}", sido, e.getMessage());
            return null;
        }
    }
    
    private String buildApiUrl(String sido, int numOfRows, int pageNo) {
        String baseUrl = "http://openapi.onbid.co.kr/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList";
        
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("DPSL_MTD_CD", "0001")
                .queryParam("CTGR_HIRK_ID", "10000")
                .queryParam("CTGR_HIRK_ID_MID", "10100")
                .queryParam("SIDO", sido)
                .build()
                .encode()
                .toUriString();
    }
}