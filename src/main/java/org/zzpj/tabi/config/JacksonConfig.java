package org.zzpj.tabi.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.UUID;

import org.zzpj.tabi.config.deserializers.UUIDDeserializer;

@Configuration
public class JacksonConfig {
    @Bean
    public Module uuidModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(UUID.class, new UUIDDeserializer());
        return module;
    }
}
