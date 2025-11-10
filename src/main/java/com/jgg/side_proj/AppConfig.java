package com.jgg.side_proj;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class AppConfig {

    // 💡 XML 메시지를 처리할 수 있도록 RestTemplate을 설정합니다.
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // XML 컨버터를 추가하여 XML 응답을 자바 객체로 변환할 수 있게 합니다.
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter(new XmlMapper()));

        // JSON 응답도 처리할 수 있도록 기본 컨버터를 유지합니다.
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }
}