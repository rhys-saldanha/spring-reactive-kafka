package org.rhyssaldanha.springreactivekafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.List;

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfiguration {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("numbers")
                .partitions(2)
                .build();
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, Integer> reactiveKafkaProducerTemplate(final KafkaProperties properties) {
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(properties.buildProducerProperties()));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Integer> reactiveKafkaConsumerTemplate(final KafkaProperties properties) {
        return new ReactiveKafkaConsumerTemplate<>(ReceiverOptions.<String, Integer>create(properties.buildConsumerProperties()).subscription(List.of("numbers")));
    }
}
