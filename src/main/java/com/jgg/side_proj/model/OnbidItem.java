package com.jgg.side_proj.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OnbidItem {
    private Long id;
    private String cltrMnmtNo;
    private String cltrNo;
    private String cltrNm;
    private String dpslMtdCd;
    private String dpslMtdNm;
    private String ctgrHirkId;
    private String ctgrHirkNm;
    private String ctgrHirkIdMid;
    private String ctgrHirkNmMid;
    private String sido;
    private String dtlAddr;
    private Long goodsPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}