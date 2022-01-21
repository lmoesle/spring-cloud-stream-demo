package de.springclouddemo.producer;

import de.springclouddemo.event.ChatEvent;
import de.springclouddemo.pojo.ChatMessage;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;

@Component
@NoArgsConstructor
public class Producer {

    private final Random random = new Random();

    // send periodically Chat Message to kafka
    @Bean
    public Supplier<ChatEvent> send() {
        return () -> new ChatEvent(new ChatMessage("Chat Message " + random.nextInt()));
    }

}
