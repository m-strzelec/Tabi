package org.zzpj.tabi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ZZPJ", version = "1.0", description = "ZZPJ"))
public class TabiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabiApplication.class, args);
	}

}
