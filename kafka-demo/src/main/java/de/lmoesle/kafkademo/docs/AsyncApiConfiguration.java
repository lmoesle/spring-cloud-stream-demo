package de.lmoesle.kafkademo.docs;

import com.asyncapi.v2.binding.kafka.KafkaOperationBinding;
import com.asyncapi.v2.model.info.Info;
import com.asyncapi.v2.model.server.Server;
import de.lmoesle.kafkademo.docs.configuration.SpringCloudStreamProperties;
import de.lmoesle.kafkademo.dto.MessageDto;
import io.github.stavshamir.springwolf.asyncapi.types.ProducerData;
import io.github.stavshamir.springwolf.configuration.AsyncApiDocket;
import io.github.stavshamir.springwolf.configuration.EnableAsyncApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableAsyncApi
public class AsyncApiConfiguration {

    private final SpringCloudStreamProperties scsProperties;
    private final String CONSUMERS_BASE_PACKAGE = "de.lmoesle.kafkademo.consumer";

    @Bean
    public AsyncApiDocket asyncApiDocket() {
        Info info = Info.builder()
                .version("1.0.0")
                .title("Kafka Demo")
                .build();

        Server kafkaServer = Server.builder()
                .protocol(this.scsProperties.getDefaultBinder())
                .url(this.scsProperties.getBroker())
                .build();

        ProducerData sendMessageProducer = ProducerData.builder()
                .channelName("kafka-demo-stream1")
                .binding(Map.of(this.scsProperties.getDefaultBinder(), new KafkaOperationBinding()))
                .payloadType(MessageDto.class)
                .build();

        return AsyncApiDocket.builder()
                .basePackage(this.CONSUMERS_BASE_PACKAGE)
                .info(info)
                .server("kafka", kafkaServer)
                .producer(sendMessageProducer)
                .build();
    }

}
