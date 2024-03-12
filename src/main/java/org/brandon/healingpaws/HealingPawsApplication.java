package org.brandon.healingpaws;

import org.brandon.healingpaws.client.Client;
import org.brandon.healingpaws.client.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class HealingPawsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealingPawsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ClientService clientService) {
        return args -> {
            var client = Client.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@gmail.com")
                    .phone("5555555555")
                    .password("password")
                    .build();

            clientService.saveClient(client);
        };
    }

}
