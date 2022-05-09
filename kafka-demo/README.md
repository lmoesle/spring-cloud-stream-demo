# Kafka Demo

Voraussetzung: Kafka ist über localhost:9092 erreichbar.

```
mvn clean install
mvn spring-boot:run
```

Die Anwendung erstellt das Kafka Topic *kafka-demo-stream*.
Die Anwendung ist sowohl ein Publisher als auch ein Subscriber dieses Topics.

* Publisher: Veröffentlicht Nachrichten im *kafka-demo-stream*
* Subscriber: Erhält Nachrichten aus dem *kafka-demo-stream* Topic und loggt diese.

Um eine Nachricht zu veröffentlichen, muss ein HTTP Request (Beispiel [message.http](message.http)) an die Anwendung gesandt werden.

## Async API documentation with springwolf

1. APIs dokumentieren (Consumer and Producer) 

```java
@DocumentAsyncAPI(payload = MessageDto.class)
public class ConsumerConfiguration {

    @Bean
    public Consumer<Message<MessageDto>> receiveMessage() {
        return message -> {
            ...
        };
    }

}
```

2. Bean mit AsyncApiDocket erstellen

```java
package de.lmoesle.kafkademo.docs;

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

        return AsyncApiDocket.builder()
                .basePackage("de.lmoesle.kafkademo")
                .info(info)
                .server("kafka", kafkaServer)
                .build();
    }

}
```
