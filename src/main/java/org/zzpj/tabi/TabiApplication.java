package org.zzpj.tabi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Tabi", version = "1.0", description = "A travel agency management system"))
public class TabiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabiApplication.class, args);
	}

}
