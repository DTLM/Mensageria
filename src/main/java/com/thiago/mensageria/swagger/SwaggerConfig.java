package com.thiago.mensageria.swagger;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private Contact contato() {
		return new Contact(
				"teste",
				"http://localhost:8080/mensageria/",
				"teste@gmail.com");
	}
	
	private ApiInfoBuilder informacoesApi() {
		ApiInfoBuilder builder = new ApiInfoBuilder();
		builder.title("Swagger para mensageria");
		builder.description("Treino com mensageria.");
		builder.version("1.0");
		builder.license("Licença - local");
		builder.licenseUrl("");
		builder.contact(this.contato());
		return builder;
	}
	
	@Bean
	public Docket Api() {
		Docket doc = new Docket(DocumentationType.SWAGGER_2);
		doc.select()
		.apis(RequestHandlerSelectors.basePackage("com.thiago.mensageria.controler"))
		.paths(PathSelectors.any())
		.build()
		.apiInfo(this.informacoesApi().build())
		.consumes(new HashSet<String>(Arrays.asList("application/json")))
		.produces(new HashSet<String>(Arrays.asList("application/json")));
		
		return doc;
	}
}
