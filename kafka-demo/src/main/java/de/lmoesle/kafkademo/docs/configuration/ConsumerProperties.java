package de.lmoesle.kafkademo.docs.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConsumerProperties {

    @Value("${spring.cloud.stream.bindings.receiveMessage-in-0.destination}")
    private String destination;
    @Value("${spring.cloud.stream.bindings.receiveMessage-in-0.group}")
    private String group;

}
