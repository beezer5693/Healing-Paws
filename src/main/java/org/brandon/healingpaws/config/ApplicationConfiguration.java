package org.brandon.healingpaws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ApplicationConfiguration {

    @Bean
    AuditorAware<?> auditorAware() {
        return null;
    }
}
