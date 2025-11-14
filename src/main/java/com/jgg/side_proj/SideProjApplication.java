package com.jgg.side_proj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.jgg.side_proj.mapper")
public class SideProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(SideProjApplication.class, args);
	}

}