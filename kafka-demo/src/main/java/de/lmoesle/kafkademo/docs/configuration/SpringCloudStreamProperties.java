package de.lmoesle.kafkademo.docs.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class SpringCloudStreamProperties {

    @Value("${spring.cloud.stream.default-binder}")
    private String defaultBinder;

    @Value("${spring.cloud.stream.kafka.binder.brokers}")
    private String broker;

    @Value("#{'${spring.cloud.function.definition}'.split(';')}")
    private List<String> definitions;

}
