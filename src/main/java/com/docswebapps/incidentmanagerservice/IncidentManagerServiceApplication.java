package com.docswebapps.incidentmanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IncidentManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentManagerServiceApplication.class, args);
	}

}
