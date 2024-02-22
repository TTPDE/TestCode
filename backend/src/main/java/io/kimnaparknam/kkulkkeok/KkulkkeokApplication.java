package io.kimnaparknam.kkulkkeok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class KkulkkeokApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkulkkeokApplication.class, args);
    }

}
