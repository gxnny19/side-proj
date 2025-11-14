package com.jgg.side_proj.service;

import com.jgg.side_proj.dto.KamcoApiResponse;
import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.mapper.OnbidMapper;
import com.jgg.side_proj.repository.OnbidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnbidService {

    private static final Logger logger = LoggerFactory.getLogger(OnbidService.class);
    
    @Value("${api.camco.serviceKey}")
    private String serviceKey;

    private final RestTemplate restTemplate;
    private final OnbidRepository repository;
    private final OnbidMapper mapper;


    
    public int saveItems(String sido) {
        try {
            String baseUrl = "http://openapi.onbid.co.kr/openapi/services/kamcoPbsalThingInquireSvc/getKamcoPbctCltrList";

            String url = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", 100)
                    .queryParam("pageNo", 1)
                    .queryParam("DPSL_MTD_CD", "0001")
                    .queryParam("CTGR_HIRK_ID", "10000")
                    .queryParam("CTGR_HIRK_ID_MID", "10100")
                    .queryParam("SIDO", sido)
                    .build()
                    .encode()
                    .toUriString();

            logger.info("API URL: {}", url);
            
            String xmlResponse = restTemplate.getForObject(url, String.class);
            logger.info("XML Response: {}", xmlResponse);
            
            KamcoApiResponse response = restTemplate.getForObject(url, KamcoApiResponse.class);
            logger.info("Parsed Response: {}", response);

            if (response == null ||
                    response.getBody() == null ||
                    response.getBody().getItems() == null ||
                    response.getBody().getItems().getItem() == null) {
                logger.warn("API 응답이 비어있습니다: {}", sido);
                return 0;
            }

            List<KamcoItemDto> list = response.getBody().getItems().getItem();

            int count = 0;
            for (KamcoItemDto dto : list) {
                // 중복 체크
                if (!repository.existsByCltrMnmtNo(dto.getCltrMnmtNo())) {
                    OnbidEntity entity = mapper.toEntity(dto);
                    repository.save(entity);
                    count++;
                } else {
                    logger.debug("중복 데이터 스킵: {}", dto.getCltrMnmtNo());
                }
            }
            return count;
        } catch (Exception e) {
            logger.error("온비드 API 호출 오류: {}", e.getMessage());
            return 0;
        }
    }
    
    public void fetchAndSaveOnbidData() {
        saveItems("경상북도");
    }
    
    public List<OnbidEntity> getAllItems() {
        return repository.findAll();
    }
}