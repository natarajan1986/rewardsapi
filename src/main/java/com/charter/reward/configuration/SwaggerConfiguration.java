package com.charter.reward.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI springShopOpenAPIV1() {
		return new OpenAPI()
				.info(new Info().title("Reward Service").description("Reward Points and transaction Service For banking").version("v1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("Service will deal with banking transactions").url(""));
	}
}
