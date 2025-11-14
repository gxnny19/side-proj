package com.jgg.side_proj.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class OnbidItemDto {
    @JacksonXmlProperty(localName = "cltrMngtNo")
    private String cltrMngtNo;
    
    @JacksonXmlProperty(localName = "pbctNm")
    private String pbctNm;
    
    @JacksonXmlProperty(localName = "sido")
    private String sido;
    
    @JacksonXmlProperty(localName = "sgk")
    private String sgk;
    
    @JacksonXmlProperty(localName = "pbctBegDt")
    private String pbctBegDt;
    
    @JacksonXmlProperty(localName = "pbctClsDt")
    private String pbctClsDt;
    
    @JacksonXmlProperty(localName = "openPrice")
    private String openPrice;
}