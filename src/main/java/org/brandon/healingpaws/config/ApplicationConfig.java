package org.brandon.healingpaws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.brandon.healingpaws.audit.ApplicationAuditorAware;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ApplicationConfig {
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
