package com.jgg.side_proj.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class KamcoItemDto {

    @JacksonXmlProperty(localName = "CLTR_MNMT_NO")
    private String cltrMnmtNo;

    @JacksonXmlProperty(localName = "CLTR_NM")
    private String cltrNm;

    @JacksonXmlProperty(localName = "SIDO")
    private String sido;

    @JacksonXmlProperty(localName = "SGK")
    private String sgk;

    @JacksonXmlProperty(localName = "PBCT_BEG_DTM")
    private String pbctBegDtm;

    @JacksonXmlProperty(localName = "PBCT_CLS_DTM")
    private String pbctClsDtm;

    @JacksonXmlProperty(localName = "MIN_BID_PRC")
    private String minBidPrc;

    @JacksonXmlProperty(localName = "CTGR_FULL_NM")
    private String ctgrFullNm;

    @JacksonXmlProperty(localName = "PASL_ASSES_AVG_AMT")
    private String paslAssesAvgAmt;

    @JacksonXmlProperty(localName = "PBCT_CLS_STAT_NM")
    private String pbctClsStatNm;
}
