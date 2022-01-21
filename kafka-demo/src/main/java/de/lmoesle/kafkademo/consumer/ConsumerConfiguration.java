package de.lmoesle.kafkademo.consumer;

import de.lmoesle.kafkademo.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConsumerConfiguration {

    @Bean
    public Consumer<Message<MessageDto>> receiveMessage() {
        return message -> {
            log.info(message.getPayload().getMessage());
        };
    }

}
