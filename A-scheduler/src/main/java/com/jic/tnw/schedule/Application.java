package com.jic.tnw.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableScheduling
@ComponentScan({ "com.jic.tnw"})
public class Application {


	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}