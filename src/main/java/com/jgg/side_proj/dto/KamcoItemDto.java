package com.jgg.side_proj.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class KamcoItemDto {

    @JacksonXmlProperty(localName = "RNUM")
    private Integer rnum;

    @JacksonXmlProperty(localName = "PLNM_NO")
    private Long plnmNo;

    @JacksonXmlProperty(localName = "PBCT_NO")
    private Long pbctNo;

    @JacksonXmlProperty(localName = "PBCT_CDTN_NO")
    private Long pbctCdtnNo;

    @JacksonXmlProperty(localName = "CLTR_NO")
    private Long cltrNo;

    @JacksonXmlProperty(localName = "CLTR_HSTR_NO")
    private Long cltrHstrNo;

    @JacksonXmlProperty(localName = "SCRN_GRP_CD")
    private String scrnGrpCd;

    @JacksonXmlProperty(localName = "CTGR_FULL_NM")
    private String ctgrFullNm;

    @JacksonXmlProperty(localName = "BID_MNMT_NO")
    private String bidMnmtNo;

    @JacksonXmlProperty(localName = "CLTR_NM")
    private String cltrNm;

    @JacksonXmlProperty(localName = "CLTR_MNMT_NO")
    private String cltrMnmtNo;

    @JacksonXmlProperty(localName = "LDNM_ADRS")
    private String ldnmAdrs;

    @JacksonXmlProperty(localName = "NMRD_ADRS")
    private String nmrdAdrs;

    @JacksonXmlProperty(localName = "LDNM_PNU")
    private String ldnmPnu;

    @JacksonXmlProperty(localName = "DPSL_MTD_CD")
    private String dpslMtdCd;

    @JacksonXmlProperty(localName = "DPSL_MTD_NM")
    private String dpslMtdNm;

    @JacksonXmlProperty(localName = "BID_MTD_NM")
    private String bidMtdNm;

    @JacksonXmlProperty(localName = "MIN_BID_PRC")
    private Long minBidPrc;

    @JacksonXmlProperty(localName = "APSL_ASES_AVG_AMT")
    private Long apslAsesAvgAmt;

    @JacksonXmlProperty(localName = "FEE_RATE")
    private String feeRate;

    @JacksonXmlProperty(localName = "PBCT_BEGN_DTM")
    private String pbctBegnDtm;

    @JacksonXmlProperty(localName = "PBCT_CLS_DTM")
    private String pbctClsDtm;

    @JacksonXmlProperty(localName = "PBCT_CLTR_STAT_NM")
    private String pbctCltrStatNm;

    @JacksonXmlProperty(localName = "USCBD_CNT")
    private Integer uscbdCnt;

    @JacksonXmlProperty(localName = "IQRY_CNT")
    private Integer iqryCnt;

    @JacksonXmlProperty(localName = "GOODS_NM")
    private String goodsNm;
}