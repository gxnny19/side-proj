package com.jgg.side_proj.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@JacksonXmlRootElement(localName = "response")
@Data
public class KamcoApiResponse {

    @JacksonXmlProperty(localName = "header")
    private Header header;

    @JacksonXmlProperty(localName = "body")
    private Body body;

    @Data
    public static class Header {
        @JacksonXmlProperty(localName = "resultCode")
        private String resultCode;

        @JacksonXmlProperty(localName = "resultMsg")
        private String resultMsg;
    }

    @Data
    public static class Body {
        @JacksonXmlProperty(localName = "items")
        private Items items;

        @JacksonXmlProperty(localName = "numOfRows")
        private String numOfRows;

        @JacksonXmlProperty(localName = "pageNo")
        private String pageNo;

        @JacksonXmlProperty(localName = "totalCount")
        private String totalCount;
    }

    @Data
    public static class Items {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "item")
        private List<KamcoItemDto> item;
    }
}