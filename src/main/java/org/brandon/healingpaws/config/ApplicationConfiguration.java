package org.brandon.healingpaws.config;

import org.brandon.healingpaws.auditing.ApplicationAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditorAware();
    }
}
