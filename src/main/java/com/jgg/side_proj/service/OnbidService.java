package com.jgg.side_proj.service;

import com.jgg.side_proj.model.OnbidResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OnbidService {

    @Value("${api.camco.serviceKey}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    public OnbidService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OnbidResponse searchOnbidItems(String sido, String sgk, String openPriceFrom) {

        String baseUrl = "http://openapi.onbid.co.kr/openapi/services/kamcoPbsalThingInquireSvc/getKamcoPbctCltrList";

        // Spring의 UriComponentsBuilder에 모든 파라미터를 추가합니다.
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                // 1. serviceKey를 포함합니다.
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)

                // 2. 숨겨진 필수 변수: API가 내부적으로 요구하는 화면/스크린 ID 추가
                .queryParam("SCRN_ID", "LPMN101M001")
                .queryParam("SCRN_NM", "온비드 공매정보")

                // 3. 필수 검색 조건: 매각 방식 및 물건 분류 코드
                .queryParam("DPSL_MTD_CD", "0001")
                .queryParam("CTGR_HIRK_ID", "10000")
                .queryParam("CTGR_HIRK_ID_MID", "10100")

                // 사용자가 입력한 검색 조건
                .queryParam("SIDO", sido)
                .queryParam("SGK", sgk)
                .queryParam("OPEN_PRICE_FROM", openPriceFrom);

        // build().encode().toUriString()이 모든 파라미터를 UTF-8로 인코딩합니다. (URL 인코딩 최종 해결)
        String url = builder.build().encode().toUriString();

        System.out.println("🚨 최종 API 요청 URL: " + url); // <-- 이 URL이 최종 진단용입니다.

        OnbidResponse result = restTemplate.getForObject(url, OnbidResponse.class);

        return result;
    }
}