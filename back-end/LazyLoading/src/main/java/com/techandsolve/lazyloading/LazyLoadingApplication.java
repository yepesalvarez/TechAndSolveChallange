package com.techandsolve.lazyloading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "com.techandsolve.lazyloading.dominio")
@ComponentScan(basePackages = {"com.techandsolve.lazyloading.controlador","com.techandsolve.lazyloading.repositorio",
		"com.techandsolve.lazyloading.servicio"})
public class LazyLoadingApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(LazyLoadingApplication.class, args);
	}
	
}

