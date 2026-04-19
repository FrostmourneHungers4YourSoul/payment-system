package org.example.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@SpringBootApplication
public class AuditServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(AuditServiceApplication.class, args);
    }
}
