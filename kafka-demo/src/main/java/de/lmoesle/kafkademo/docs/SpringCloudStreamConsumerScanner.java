package de.lmoesle.kafkademo.docs;

import com.asyncapi.v2.binding.kafka.KafkaOperationBinding;
import com.asyncapi.v2.model.channel.ChannelItem;
import com.asyncapi.v2.model.channel.operation.Operation;
import de.lmoesle.kafkademo.dto.MessageDto;
import io.github.stavshamir.springwolf.asyncapi.scanners.channels.ChannelsScanner;
import io.github.stavshamir.springwolf.asyncapi.types.channel.operation.message.Message;
import io.github.stavshamir.springwolf.asyncapi.types.channel.operation.message.PayloadReference;
import io.github.stavshamir.springwolf.schemas.SchemasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class SpringCloudStreamConsumerScanner implements ChannelsScanner {

    private final SchemasService schemasService;

    @Override
    public Map<String, ChannelItem> scan() {
        Class<?> payloadType = MessageDto.class;

        String modelName = this.schemasService.register(payloadType);

        Message msg = Message.builder()
                .name(payloadType.getName())
                .title(modelName)
                .payload(PayloadReference.fromModelName(modelName))
                .build();

        var binding = new KafkaOperationBinding();
        binding.setGroupId("kafka-demo");

        Operation operation = Operation.builder()
                .message(msg)
                .bindings(Map.of("kafka", binding))
                .build();

        var channelItem = ChannelItem.builder()
                .publish(operation)
                .description("Consumer that receives messages")
                .build();

        return Map.of("kafka-demo-stream1", channelItem);
    }

}
