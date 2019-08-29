package com.intrasoftintl.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.intrasoftintl.ACUnit"})
@ComponentScan({"com.intrasoftintl.iot"})
@ComponentScan({"com.intrasoftintl.Lock"})
@ComponentScan({"com.intrasoftintl.SmartFridge"})
@ComponentScan({"com.intrasoftintl.CoffeeMaker"})
public class IotApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotApplication.class, args);
		
		
	}

}