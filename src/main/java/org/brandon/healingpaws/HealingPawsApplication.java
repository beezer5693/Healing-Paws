package org.brandon.healingpaws;

import org.brandon.healingpaws.client.Client;
import org.brandon.healingpaws.client.ClientRepository;
import org.brandon.healingpaws.client.ClientServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HealingPawsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealingPawsApplication.class, args);
    }

}
