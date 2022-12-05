package br.com.studenton.sptechforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SptechforumApplication {

	public static void main(String[] args) {
		SpringApplication.run(SptechforumApplication.class, args);
	}

	
	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
						.allowedHeaders("*")
						.allowedOrigins("*");
			}
		};
	}
}
