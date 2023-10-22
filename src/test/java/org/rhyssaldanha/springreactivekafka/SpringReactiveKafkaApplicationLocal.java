package org.rhyssaldanha.springreactivekafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringReactiveKafkaApplicationLocal {
    public static void main(final String[] args) {
        SpringApplication
                .from(SpringReactiveKafkaApplication::main)
                .with(SpringReactiveKafkaApplicationLocal.class)
                .run(args);
    }
}
