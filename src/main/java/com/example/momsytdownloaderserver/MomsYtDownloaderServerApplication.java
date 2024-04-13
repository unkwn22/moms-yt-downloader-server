package com.example.momsytdownloaderserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MomsYtDownloaderServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomsYtDownloaderServerApplication.class, args);
	}

}
