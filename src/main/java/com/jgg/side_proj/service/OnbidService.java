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

    public OnbidResponse searchBySido(String sido) {
        String baseUrl = "http://openapi.onbid.co.kr/openapi/services/kamcoPbsalThingInquireSvc/getKamcoPbctCltrList";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 20)
                .queryParam("pageNo", 1)
                .queryParam("SCRN_ID", "LPMN101M001")
                .queryParam("SCRN_NM", "온비드 공매정보")
                .queryParam("DPSL_MTD_CD", "0001")
                .queryParam("CTGR_HIRK_ID", "10000")
                .queryParam("CTGR_HIRK_ID_MID", "10100")
                .queryParam("SIDO", sido);

        String url = builder.build().encode().toUriString();
        return restTemplate.getForObject(url, OnbidResponse.class);
    }
}