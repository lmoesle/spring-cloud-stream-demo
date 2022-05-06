package de.lmoesle.kafkademo.docs.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.stream")
public class SpringCloudStreamProperties {

    private Map<String, Map<String, String>> bindings;

}
