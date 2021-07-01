package com.instil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ServerDemosApplication {

	@Bean(name="flintstones")
	public List<String> sample1() {
		return List.of("Fred", "Wilma", "Barney", "Betty", "Pebbles");
	}

	@Bean(name="simpsons")
	public List<String> sample2() {
		return List.of("Homer", "Marge", "Bart", "Liza", "Maggie");
	}


	public static void main(String[] args) {
		SpringApplication.run(ServerDemosApplication.class, args);
	}

}
