package com.crossvale;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.kie.api.KieBase;

@SpringBootApplication
public class BalancerApp {
	
	private static File resrouceDirectory = new File("work");
	
	public static void main(String[] args) {
		SpringApplication.run(BalancerApp.class, args);

	}

	/*@Bean
	public KieContainer kieContainer() {
        
		return KieServices.Factory.get().getKieClasspathContainer();
	}*/
}
