package de.lmoesle.kafkademo.docs;

import com.asyncapi.v2.binding.kafka.KafkaOperationBinding;
import com.asyncapi.v2.model.info.Info;
import com.asyncapi.v2.model.server.Server;
import de.lmoesle.kafkademo.dto.MessageDto;
import io.github.stavshamir.springwolf.asyncapi.types.ProducerData;
import io.github.stavshamir.springwolf.configuration.AsyncApiDocket;
import io.github.stavshamir.springwolf.configuration.EnableAsyncApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableAsyncApi
public class AsyncApiConfiguration {

    private final String BOOTSTRAP_SERVERS = "kafka:29092";
    private final String CONSUMERS_BASE_PACKAGE = "de.lmoesle.kafkademo.consumer";

    @Bean
    public AsyncApiDocket asyncApiDocket() {
        Info info = Info.builder()
                .version("1.0.0")
                .title("Kafka Demo")
                .build();

        Server kafkaServer = Server.builder()
                .protocol("kafka")
                .url(BOOTSTRAP_SERVERS)
                .build();

        ProducerData sendMessageProducer = ProducerData.builder()
                .channelName("kafka-demo-stream")
                .binding(Map.of("kafka", new KafkaOperationBinding()))
                .payloadType(MessageDto.class)
                .build();

        return AsyncApiDocket.builder()
                .basePackage(CONSUMERS_BASE_PACKAGE)
                .info(info)
                .server("kafka", kafkaServer)
                .producer(sendMessageProducer)
                .build();
    }

}
