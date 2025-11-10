package com.jgg.side_proj.model;

import lombok.Data;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Data // Lombok: getter, setter 등을 자동으로 만들어 줍니다.
public class OnbidItem {

    // @JacksonXmlProperty: XML 태그 이름과 Java 변수 이름을 연결합니다.

    // 물건관리번호
    @JacksonXmlProperty(localName = "CLTR_MNMT_NO")
    private String cltrMnmtNo;

    // 물건소재지 (시/도)
    @JacksonXmlProperty(localName = "SIDO")
    private String sido;

    // 최저입찰가 (예시)
    @JacksonXmlProperty(localName = "MIN_BID_PRC")
    private String minBidPrc;

    // ... (필요한 항목들을 여기에 계속 추가해야 합니다.)
}