package com.jgg.side_proj.dto;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String sido;
    private String sgk;
    private String ctgrHirkId;
    private String ctgrHirkIdMid;
    private String dpslMtdCd;
    private Integer numOfRows;
    private Integer pageNo;
    
    public SearchRequestDto() {
        // 기본값 설정
        this.dpslMtdCd = "0001";
        this.ctgrHirkId = "10000";
        this.ctgrHirkIdMid = "10100";
        this.numOfRows = 10;
        this.pageNo = 1;
    }
}