package de.lmoesle.kafkademo.docs;

import com.asyncapi.v2.model.info.Info;
import com.asyncapi.v2.model.server.Server;
import io.github.stavshamir.springwolf.configuration.AsyncApiDocket;
import io.github.stavshamir.springwolf.configuration.EnableAsyncApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableAsyncApi
public class AsyncApiConfiguration {

    @Value("${spring.cloud.stream.default-binder}")
    private String defaultBinder;

    @Value("${spring.cloud.stream.kafka.binder.brokers}")
    private String broker;

    @Bean
    public AsyncApiDocket asyncApiDocket() {
        Info info = Info.builder()
                .version("1.0.0")
                .title("Kafka Demo")
                .build();

        Server kafkaServer = Server.builder()
                .protocol(this.defaultBinder)
                .url(this.broker)
                .build();

//        ProducerData sendMessageProducer = ProducerData.builder()
//                .channelName("kafka-demo-stream1")
//                .binding(Map.of(this.defaultBinder, new KafkaOperationBinding()))
//                .payloadType(MessageDto.class)
//                .build();

        return AsyncApiDocket.builder()
                .basePackage("de.lmoesle.kafkademo")
                .info(info)
                .server("kafka", kafkaServer)
//                .producer(sendMessageProducer)
                .build();
    }

}
