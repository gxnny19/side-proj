package com.jgg.side_proj.service;

import com.jgg.side_proj.dto.KamcoApiResponse;
import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.mapper.KamcoMapper;
import com.jgg.side_proj.repository.OnbidRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class OnbidService {

    @Value("${api.camco.serviceKey}")
    private String serviceKey;

    private final RestTemplate restTemplate;
    private final OnbidRepository repository;
    private final KamcoMapper mapper;

    public OnbidService(RestTemplate restTemplate, OnbidRepository repository, KamcoMapper mapper) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.mapper = mapper;
    }

    public KamcoApiResponse search(com.jgg.side_proj.dto.SearchRequestDto request) {
        String baseUrl = "http://openapi.onbid.co.kr/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", request.getNumOfRows())
                .queryParam("pageNo", request.getPageNo())
                .queryParam("DPSL_MTD_CD", request.getDpslMtdCd())
                .queryParam("CTGR_HIRK_ID", request.getCtgrHirkId())
                .queryParam("CTGR_HIRK_ID_MID", request.getCtgrHirkIdMid());
                
        // 선택적 파라미터들
        if (request.getSido() != null && !request.getSido().trim().isEmpty()) {
            builder.queryParam("SIDO", request.getSido());
        }
        if (request.getSgk() != null && !request.getSgk().trim().isEmpty()) {
            builder.queryParam("SGK", request.getSgk());
        }

        String url = builder.build().encode().toUriString();
        System.out.println("최종 API URL: " + url);
        
        try {
            String xmlResponse = restTemplate.getForObject(url, String.class);
            System.out.println("XML 응답: " + xmlResponse);
        } catch (Exception e) {
            System.out.println("XML 응답 오류: " + e.getMessage());
        }
        
        KamcoApiResponse response = restTemplate.getForObject(url, KamcoApiResponse.class);
        System.out.println("파싱된 응답: " + response);
        
        // 데이터 저장 (중복 처리)
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            List<KamcoItemDto> items = response.getBody().getItems().getItem();
            if (items != null) {
                int savedCount = 0;
                int duplicateCount = 0;
                
                for (KamcoItemDto item : items) {
                    if (item.getCltrMnmtNo() != null && !item.getCltrMnmtNo().trim().isEmpty()) {
                        // 복합키 기반 중복 체크 (물건번호 + 입찰시작시간 + 최저입찰가)
                        boolean isDuplicate = repository.existsByCompositeKey(
                            item.getCltrMnmtNo(), 
                            item.getPbctBegnDtm(), 
                            item.getMinBidPrc()
                        );
                        
                        if (!isDuplicate) {
                            try {
                                OnbidEntity entity = mapper.toEntity(item);
                                repository.save(entity);
                                savedCount++;
                                System.out.println("저장됨: " + item.getCltrMnmtNo() + " (입찰시간: " + item.getPbctBegnDtm() + ", 가격: " + item.getMinBidPrc() + ")");
                            } catch (Exception e) {
                                System.out.println("저장 실패: " + item.getCltrMnmtNo() + " - " + e.getMessage());
                            }
                        } else {
                            duplicateCount++;
                            System.out.println("중복 스킵: " + item.getCltrMnmtNo() + " (입찰시간: " + item.getPbctBegnDtm() + ", 가격: " + item.getMinBidPrc() + ")");
                        }
                    }
                }
                
                System.out.println("저장 완료: " + savedCount + "개, 중복 스킵: " + duplicateCount + "개");
            }
        }
        
        return response;
    }
    
    public List<OnbidEntity> getSavedItems(String sido) {
        System.out.println("검색 요청 지역: " + sido);
        List<OnbidEntity> results = repository.findBySido(sido);
        System.out.println("검색 결과 개수: " + results.size());
        
        // 전체 데이터 확인
        List<OnbidEntity> allData = repository.findAll();
        System.out.println("전체 데이터 개수: " + allData.size());
        
        if (!allData.isEmpty()) {
            System.out.println("첫 번째 데이터 주소: " + allData.get(0).getLdnmAdrs());
        }
        
        return results;
    }
}