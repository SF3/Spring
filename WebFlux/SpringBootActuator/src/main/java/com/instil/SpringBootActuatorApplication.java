package com.instil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@EnableWebFluxSecurity
public class SpringBootActuatorApplication {
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.authorizeExchange()
				.pathMatchers("/**").permitAll()
				.anyExchange().authenticated()
				.and().build();
	}

	@Bean
	public HttpTraceRepository pageTracing() {
		return new InMemoryHttpTraceRepository();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootActuatorApplication.class, args);
	}
}
