package com.jgg.side_proj.dto;

import lombok.Data;

@Data
public class KamcoItemDto {
    private String thingNo;      // 물건번호
    private String thingNm;      // 물건명
    private String apslAsesAmt;  // 감정가
    private String dpslMtdNm;    // 처분방법
    private String sidoNm;       // 시도명
    private String addr;         // 주소
    private String bidDt;        // 입찰일
    private String category;     // 카테고리
}