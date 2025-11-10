package com.jgg.side_proj.model;

import lombok.Data;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

// XML의 최상위 태그 이름으로 설정합니다. (캠코 API 응답에서 확인 가능)
@JacksonXmlRootElement(localName = "response")
@Data
public class OnbidResponse {

    @JacksonXmlProperty(localName = "header")
    private Object header; // 헤더 부분은 간단히 Object로 처리

    @JacksonXmlProperty(localName = "body")
    private Body body;

    @Data
    public static class Body {
        @JacksonXmlProperty(localName = "items")
        private Items items;
    }

    @Data
    public static class Items {
        // <item> 태그들의 목록을 List<OnbidItem>으로 받습니다.
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<OnbidItem> item;
    }
}