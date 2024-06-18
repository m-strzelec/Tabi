package org.zzpj.tabi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:payments.properties")
public class Config {
}
