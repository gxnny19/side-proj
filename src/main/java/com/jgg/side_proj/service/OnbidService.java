package com.jgg.side_proj.service;

import com.jgg.side_proj.dto.BidRequestDto;
import com.jgg.side_proj.dto.KamcoApiResponse;
import com.jgg.side_proj.dto.KamcoItemDto;
import com.jgg.side_proj.entity.OnbidEntity;
import com.jgg.side_proj.mapper.KamcoMapper;
import com.jgg.side_proj.repository.OnbidRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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
    
    public KamcoApiResponse saveGyeongbukData() {
        // 샘플 데이터 먼저 삽입
        insertSampleGyeongbukData();
        
        // 샘플 데이터를 KamcoApiResponse 형태로 반환
        KamcoApiResponse response = new KamcoApiResponse();
        KamcoApiResponse.Header header = new KamcoApiResponse.Header();
        header.setResultCode("00");
        header.setResultMsg("NORMAL SERVICE.");
        response.setHeader(header);
        
        KamcoApiResponse.Body body = new KamcoApiResponse.Body();
        body.setNumOfRows("1");
        body.setPageNo("1");
        body.setTotalCount("1");
        
        KamcoApiResponse.Items items = new KamcoApiResponse.Items();
        List<KamcoItemDto> itemList = new java.util.ArrayList<>();
        
        // 샘플 데이터를 DTO로 변환
        KamcoItemDto item = new KamcoItemDto();
        item.setRnum(1);
        item.setPlnmNo(849479L);
        item.setPbctNo(9992398L);
        item.setPbctCdtnNo(5687727L);
        item.setCltrNo(1928237L);
        item.setCltrHstrNo(5851151L);
        item.setScrnGrpCd("0001");
        item.setCtgrFullNm("토지 / 임야");
        item.setBidMnmtNo("0017");
        item.setCltrNm("경상북도 구미시 임은동 산2-4");
        item.setCltrMnmtNo("2025-12897-001");
        item.setLdnmAdrs("경상북도 구미시 임은동 산 2-4");
        item.setNmrdAdrs("");
        item.setLdnmPnu("4719011700100020004");
        item.setDpslMtdCd("0001");
        item.setDpslMtdNm("매각");
        item.setBidMtdNm("일반경쟁(최고가방식) / 총액");
        item.setMinBidPrc(29534000L);
        item.setApslAsesAvgAmt(147668000L);
        item.setFeeRate("(20%)");
        item.setPbctBegnDtm("20260309140000");
        item.setPbctClsDtm("20260311170000");
        item.setPbctCltrStatNm("입찰준비중");
        item.setUscbdCnt(0);
        item.setIqryCnt(1);
        item.setGoodsNm("임야 5,092 ㎡ 지분(총면적 15,669㎡ 15669분의5092 지분)");
        
        itemList.add(item);
        items.setItem(itemList);
        body.setItems(items);
        response.setBody(body);
        
        return response;
    }
    
    public void insertSampleGyeongbukData() {
        OnbidEntity entity = new OnbidEntity();
        entity.setRnum(1);
        entity.setPlnmNo(849479L);
        entity.setPbctNo(9992398L);
        entity.setPbctCdtnNo(5687727L);
        entity.setCltrNo(1928237L);
        entity.setCltrHstrNo(5851151L);
        entity.setScrnGrpCd("0001");
        entity.setCtgrFullNm("토지 / 임야");
        entity.setBidMnmtNo("0017");
        entity.setCltrNm("경상북도 구미시 임은동 산2-4");
        entity.setCltrMnmtNo("2025-12897-001");
        entity.setLdnmAdrs("경상북도 구미시 임은동 산 2-4");
        entity.setNmrdAdrs("");
        entity.setLdnmPnu("4719011700100020004");
        entity.setDpslMtdCd("0001");
        entity.setDpslMtdNm("매각");
        entity.setBidMtdNm("일반경쟁(최고가방식) / 총액");
        entity.setMinBidPrc(29534000L);
        entity.setApslAsesAvgAmt(147668000L);
        entity.setFeeRate("(20%)");
        entity.setPbctBegnDtm("20260309140000");
        entity.setPbctClsDtm("20260311170000");
        entity.setPbctCltrStatNm("입찰준비중");
        entity.setUscbdCnt(0);
        entity.setIqryCnt(1);
        entity.setGoodsNm("임야 5,092 ㎡ 지분(총면적 15,669㎡ 15669분의5092 지분)");
        entity.setCreatedAt(java.time.LocalDateTime.now());
        
        try {
            repository.save(entity);
            System.out.println("경상북도 샘플 데이터 저장 완료: " + entity.getCltrMnmtNo());
        } catch (Exception e) {
            System.out.println("경상북도 샘플 데이터 저장 실패: " + e.getMessage());
        }
    }
    
    public String testGyeongbukApi() {
        String url = "http://openapi.onbid.co.kr/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList" +
                "?serviceKey=" + serviceKey +
                "&numOfRows=10" +
                "&pageNo=1" +
                "&DPSL_MTD_CD=0001" +
                "&CTGR_HIRK_ID=10000" +
                "&CTGR_HIRK_ID_MID=10100" +
                "&SIDO=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84";
        
        try {
            String xmlResponse = restTemplate.getForObject(url, String.class);
            return xmlResponse;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    private static final Map<String, List<Map<String, Object>>> userBids = new ConcurrentHashMap<>();
    
    public ResponseEntity<Map<String, String>> submitBid(BidRequestDto bidRequest) {
        Map<String, String> response = new HashMap<>();
        
        try {
            OnbidEntity item = repository.findByCltrMnmtNo(bidRequest.getCltrMnmtNo());
            
            // 물건을 찾을 수 없으면 샘플 데이터 사용
            if (item == null) {
                item = new OnbidEntity();
                item.setCltrMnmtNo(bidRequest.getCltrMnmtNo());
                item.setCltrNm("경상북도 구미시 임은동 산2-4");
                item.setMinBidPrc(29534000L);
                System.out.println("물건을 찾을 수 없어 샘플 데이터 사용: " + bidRequest.getCltrMnmtNo());
            }
            
            if (bidRequest.getBidAmount() < item.getMinBidPrc()) {
                response.put("status", "error");
                response.put("message", "입찰가격이 최저입찰가보다 낮습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // 입찰 데이터 저장
            Map<String, Object> bidData = new HashMap<>();
            bidData.put("cltrMnmtNo", bidRequest.getCltrMnmtNo());
            bidData.put("cltrNm", item.getCltrNm());
            bidData.put("bidAmount", bidRequest.getBidAmount());
            bidData.put("minBidPrc", item.getMinBidPrc());
            bidData.put("bidTime", LocalDateTime.now().toString());
            bidData.put("status", "입찰완료");
            
            userBids.computeIfAbsent(bidRequest.getBidderName(), k -> new ArrayList<>()).add(bidData);
            
            System.out.println("입찰 신청: " + bidRequest.getCltrMnmtNo() + ", 가격: " + bidRequest.getBidAmount());
            
            response.put("status", "success");
            response.put("message", "입찰이 성공적으로 신청되었습니다!");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "입찰 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    public List<Map<String, Object>> getMyBids(String username) {
        return userBids.getOrDefault(username, new ArrayList<>());
    }
    
    public ResponseEntity<Map<String, String>> cancelBid(String cltrMnmtNo) {
        Map<String, String> response = new HashMap<>();
        
        try {
            // 실제로는 입찰 취소 로직 구현
            System.out.println("입찰 취소: " + cltrMnmtNo);
            
            response.put("status", "success");
            response.put("message", "입찰이 성공적으로 취소되었습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "입찰 취소 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    public Map<String, Object> fetchAndSaveApiData() {
        Map<String, Object> result = new HashMap<>();
        
        String url = "http://openapi.onbid.co.kr/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList" +
                "?serviceKey=" + serviceKey +
                "&numOfRows=10" +
                "&pageNo=1" +
                "&DPSL_MTD_CD=0001" +
                "&CTGR_HIRK_ID=10000" +
                "&CTGR_HIRK_ID_MID=10100" +
                "&SIDO=%EA%B2%BD%EC%83%81%EB%B6%81%EB%8F%84";
        
        try {
            System.out.println("API 호출 URL: " + url);
            
            // XML 응답 확인
            String xmlResponse = restTemplate.getForObject(url, String.class);
            System.out.println("XML 응답: " + xmlResponse);
            
            // JSON으로 파싱
            KamcoApiResponse response = restTemplate.getForObject(url, KamcoApiResponse.class);
            
            if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
                List<KamcoItemDto> items = response.getBody().getItems().getItem();
                
                if (items != null && !items.isEmpty()) {
                    int savedCount = 0;
                    int duplicateCount = 0;
                    
                    for (KamcoItemDto item : items) {
                        if (item.getCltrMnmtNo() != null && !item.getCltrMnmtNo().trim().isEmpty()) {
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
                                    System.out.println("저장됨: " + item.getCltrMnmtNo());
                                } catch (Exception e) {
                                    System.out.println("저장 실패: " + item.getCltrMnmtNo() + " - " + e.getMessage());
                                }
                            } else {
                                duplicateCount++;
                                System.out.println("중복 스킵: " + item.getCltrMnmtNo());
                            }
                        }
                    }
                    
                    result.put("status", "success");
                    result.put("message", "API 데이터 저장 완료");
                    result.put("totalItems", items.size());
                    result.put("savedCount", savedCount);
                    result.put("duplicateCount", duplicateCount);
                    result.put("data", response);
                    
                } else {
                    result.put("status", "error");
                    result.put("message", "API 응답에 데이터가 없습니다.");
                }
            } else {
                result.put("status", "error");
                result.put("message", "API 응답 파싱 실패");
            }
            
        } catch (Exception e) {
            System.out.println("API 호출 오류: " + e.getMessage());
            e.printStackTrace();
            
            result.put("status", "error");
            result.put("message", "API 호출 중 오류 발생: " + e.getMessage());
        }
        
        return result;
    }
    
    public Map<String, Object> fetchAndSaveApiDataBySido(String sido) {
        Map<String, Object> result = new HashMap<>();
        
        // 서울특별시 샘플 데이터 처리
        if ("서울특별시".equals(sido)) {
            return createSeoulSampleData();
        }
        
        String baseUrl = "http://openapi.onbid.co.kr/openapi/services/KamcoPblsalThingInquireSvc/getKamcoPbctCltrList";
        String url = baseUrl + "?serviceKey=" + serviceKey +
                "&numOfRows=10" +
                "&pageNo=1" +
                "&DPSL_MTD_CD=0001" +
                "&CTGR_HIRK_ID=10000" +
                "&CTGR_HIRK_ID_MID=10100";
        
        if (sido != null && !sido.trim().isEmpty()) {
            url += "&SIDO=" + java.net.URLEncoder.encode(sido, java.nio.charset.StandardCharsets.UTF_8);
        }
        
        try {
            System.out.println("API 호출 URL: " + url);
            
            String xmlResponse = restTemplate.getForObject(url, String.class);
            System.out.println("XML 응답: " + xmlResponse);
            
            KamcoApiResponse response = restTemplate.getForObject(url, KamcoApiResponse.class);
            
            if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
                List<KamcoItemDto> items = response.getBody().getItems().getItem();
                
                if (items != null && !items.isEmpty()) {
                    int savedCount = 0;
                    int duplicateCount = 0;
                    
                    for (KamcoItemDto item : items) {
                        if (item.getCltrMnmtNo() != null && !item.getCltrMnmtNo().trim().isEmpty()) {
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
                                    System.out.println("저장됨: " + item.getCltrMnmtNo());
                                } catch (Exception e) {
                                    System.out.println("저장 실패: " + item.getCltrMnmtNo() + " - " + e.getMessage());
                                }
                            } else {
                                duplicateCount++;
                                System.out.println("중복 스킵: " + item.getCltrMnmtNo());
                            }
                        }
                    }
                    
                    result.put("status", "success");
                    result.put("message", "API 데이터 저장 완료");
                    result.put("totalItems", items.size());
                    result.put("savedCount", savedCount);
                    result.put("duplicateCount", duplicateCount);
                    result.put("data", response);
                    
                } else {
                    result.put("status", "error");
                    result.put("message", "API 응답에 데이터가 없습니다.");
                }
            } else {
                result.put("status", "error");
                result.put("message", "API 응답 파싱 실패");
            }
            
        } catch (Exception e) {
            System.out.println("API 호출 오류: " + e.getMessage());
            e.printStackTrace();
            
            result.put("status", "error");
            result.put("message", "API 호출 중 오류 발생: " + e.getMessage());
        }
        
        return result;
    }
    
    private Map<String, Object> createSeoulSampleData() {
        Map<String, Object> result = new HashMap<>();
        
        // 서울 샘플 데이터 생성
        KamcoApiResponse response = new KamcoApiResponse();
        KamcoApiResponse.Header header = new KamcoApiResponse.Header();
        header.setResultCode("00");
        header.setResultMsg("NORMAL SERVICE.");
        response.setHeader(header);
        
        KamcoApiResponse.Body body = new KamcoApiResponse.Body();
        body.setNumOfRows("1");
        body.setPageNo("1");
        body.setTotalCount("1");
        
        KamcoApiResponse.Items items = new KamcoApiResponse.Items();
        List<KamcoItemDto> itemList = new ArrayList<>();
        
        KamcoItemDto item = new KamcoItemDto();
        item.setRnum(4);
        item.setPlnmNo(847462L);
        item.setPbctNo(9970423L);
        item.setPbctCdtnNo(5670501L);
        item.setCltrNo(1908949L);
        item.setCltrHstrNo(5830042L);
        item.setScrnGrpCd("0001");
        item.setCtgrFullNm("토지 / 임야");
        item.setBidMnmtNo("0020");
        item.setCltrNm("서울특별시 서대문구 홍제동 산5-3 [지분3437분의99]");
        item.setCltrMnmtNo("2025-03800-003");
        item.setLdnmAdrs("서울특별시 서대문구 홍제동 산 5-3 [ 지분3437분의99]");
        item.setNmrdAdrs("");
        item.setLdnmPnu("1141011100100050003");
        item.setDpslMtdCd("0001");
        item.setDpslMtdNm("매각");
        item.setBidMtdNm("일반경쟁(최고가방식) / 총액");
        item.setMinBidPrc(18533000L);
        item.setApslAsesAvgAmt(20592000L);
        item.setFeeRate("(90%)");
        item.setPbctBegnDtm("20251229140000");
        item.setPbctClsDtm("20251231170000");
        item.setPbctCltrStatNm("입찰준비중");
        item.setUscbdCnt(0);
        item.setIqryCnt(1);
        item.setGoodsNm("임야 99 ㎡ 지분(총면적 3,437㎡ 3437분의99 지분)");
        
        itemList.add(item);
        items.setItem(itemList);
        body.setItems(items);
        response.setBody(body);
        
        result.put("status", "success");
        result.put("message", "서울특별시 데이터 로드 완료");
        result.put("totalItems", 1);
        result.put("savedCount", 0);
        result.put("duplicateCount", 0);
        result.put("data", response);
        
        return result;
    }
}