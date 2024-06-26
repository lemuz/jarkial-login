package com.jarkial.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.cloud.netflix.eureka.EnableEurekaClient;*/

@SpringBootApplication
/*@EnableEurekaClient*/
public class LoginApplication {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

}
