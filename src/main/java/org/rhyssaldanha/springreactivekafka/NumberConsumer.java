package org.rhyssaldanha.springreactivekafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NumberConsumer {
    private final ReactiveKafkaConsumerTemplate<String, Integer> reactiveKafkaConsumerTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<Integer> startConsumer() {
        return reactiveKafkaConsumerTemplate.receiveAutoAck()
                .flatMap(this::consume);
    }

    private Mono<Integer> consume(final ConsumerRecord<String, Integer> record) {
        return Mono.just(record)
                .map(ConsumerRecord::value)
                .doOnNext(n -> log.info("Consuming: " + n));
    }
}
