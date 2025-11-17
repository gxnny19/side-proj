package com.jgg.side_proj.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OnbidEntity {
    private Long id;
    private Integer rnum;
    private Long plnmNo;
    private Long pbctNo;
    private Long pbctCdtnNo;
    private Long cltrNo;
    private Long cltrHstrNo;
    private String scrnGrpCd;
    private String ctgrFullNm;
    private String bidMnmtNo;
    private String cltrNm;
    private String cltrMnmtNo;
    private String ldnmAdrs;
    private String nmrdAdrs;
    private String ldnmPnu;
    private String dpslMtdCd;
    private String dpslMtdNm;
    private String bidMtdNm;
    private Long minBidPrc;
    private Long apslAsesAvgAmt;
    private String feeRate;
    private String pbctBegnDtm;
    private String pbctClsDtm;
    private String pbctCltrStatNm;
    private Integer uscbdCnt;
    private Integer iqryCnt;
    private String goodsNm;
    private LocalDateTime createdAt;
}