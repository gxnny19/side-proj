package com.jgg.side_proj.model;

import lombok.Data;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "response")
public class OnbidResponse {

    @JacksonXmlProperty(localName = "body")
    private Body body;

    @Data
    public static class Body {
        @JacksonXmlProperty(localName = "items")
        private Items items;
    }

    @Data
    public static class Items {
        @JacksonXmlProperty(localName = "item")
        private List<OnbidItem> item;
    }
}
