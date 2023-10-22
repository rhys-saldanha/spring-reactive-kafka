package org.rhyssaldanha.springreactivekafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class NumberProducer {
    private final ReactiveKafkaProducerTemplate<String, Integer> reactiveKafkaProducerTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<SenderResult<Void>> startProducer() {
        return Flux.<Integer>generate(sink -> sink.next((int) (Math.random() * 100)))
                .buffer(10)
                .delayElements(Duration.ofSeconds(5))
                .flatMapIterable(numbers -> numbers)
                .doOnNext(number -> log.info("Producing: " + number))
                .flatMap(number -> reactiveKafkaProducerTemplate.send("numbers", number));
    }
}
