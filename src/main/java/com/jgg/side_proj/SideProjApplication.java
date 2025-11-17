package com.jgg.side_proj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jgg.side_proj.repository")
public class SideProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(SideProjApplication.class, args);
	}

}
