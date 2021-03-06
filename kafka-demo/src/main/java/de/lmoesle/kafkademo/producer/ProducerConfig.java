package de.lmoesle.kafkademo.producer;

import de.lmoesle.kafkademo.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Slf4j
@Configuration
public class ProducerConfig {

    @Bean
    public Sinks.Many<Message<MessageDto>> createMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<MessageDto>>> sendMessage(Sinks.Many<Message<MessageDto>> messagePublisher) {
        return messagePublisher::asFlux;
    }
}
