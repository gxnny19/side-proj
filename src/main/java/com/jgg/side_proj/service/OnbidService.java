package com.jgg.side_proj.service;

import com.jgg.side_proj.dto.OnbidItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.model.OnbidItem;
import com.jgg.side_proj.model.OnbidResponse;
import com.jgg.side_proj.repository.OnbidRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
            
            OnbidResponse response = restTemplate.getForObject(url, OnbidResponse.class);
            logger.info("Parsed Response: {}", response);

            if (response == null ||
                    response.getBody() == null ||
                    response.getBody().getItems() == null ||
                    response.getBody().getItems().getItem() == null) {
                logger.warn("API 응답이 비어있습니다: {}", sido);
                return 0;
            }

            List<OnbidItem> list = response.getBody().getItems().getItem();

            int count = 0;
            for (OnbidItem dto : list) {
                OnbidEntity entity = new OnbidEntity();
                entity.setCltrMnmtNo(dto.getCltrMnmtNo());
                entity.setCltrNm(dto.getCltrNm());
                entity.setSido(dto.getSido());
                entity.setSgk(dto.getSgk());
                entity.setPbctBegDtm(dto.getPbctBegDtm());
                entity.setPbctClsDtm(dto.getPbctClsDtm());
                entity.setMinBidPrc(dto.getMinBidPrc());
                entity.setCtgrFullNm(dto.getCtgrFullNm());
                entity.setPaslAssesAvgAmt(dto.getPaslAssesAvgAmt());
                entity.setPbctClsStatNm(dto.getPbctClsStatNm());
                repository.save(entity);
                count++;
            }
            return count;
        } catch (Exception e) {
            logger.error("온비드 API 호출 오류: {}", e.getMessage());
            return 0;
        }
    }
    
    public void fetchAndSaveOnbidData() {
        try {
            String xmlResponse = restTemplate.getForObject("https://api.onbid.co.kr/openapi/services/OnbidPblancListInfoService/getOnbidPblancListInfo?serviceKey=" + serviceKey + "&numOfRows=10&pageNo=1", String.class);
            
            XmlMapper xmlMapper = new XmlMapper();
            OnbidItemDto itemDto = xmlMapper.readValue(xmlResponse, OnbidItemDto.class);
            
            OnbidEntity entity = convertToEntity(itemDto);
            repository.save(entity);
            
            logger.info("온비드 데이터 저장 완료: {}", entity.getCltrMnmtNo());
        } catch (Exception e) {
            logger.error("온비드 API 호출 오류: {}", e.getMessage());
        }
    }
    
    private OnbidEntity convertToEntity(OnbidItemDto dto) {
        OnbidEntity entity = new OnbidEntity();
        entity.setCltrMnmtNo(dto.getCltrMngtNo());
        entity.setCltrNm(dto.getPbctNm());
        entity.setSido(dto.getSido());
        entity.setSgk(dto.getSgk());
        entity.setPbctBegDtm(dto.getPbctBegDt());
        entity.setPbctClsDtm(dto.getPbctClsDt());
        entity.setMinBidPrc(dto.getOpenPrice());
        return entity;
    }
    

    
    public List<OnbidEntity> getAllItems() {
        return repository.findAll();
    }
}
