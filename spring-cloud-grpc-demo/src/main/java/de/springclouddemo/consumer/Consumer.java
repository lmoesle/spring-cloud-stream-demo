package de.springclouddemo.consumer;


import de.springclouddemo.chat.ChatGrpc;
import de.springclouddemo.chat.ConnectionInitRequest;
import de.springclouddemo.chat.ChatResponse;
import de.springclouddemo.event.ChatEvent;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@GrpcService
@Slf4j
public class Consumer extends ChatGrpc.ChatImplBase {

    private final List<StreamObserver<ChatResponse>> observers = new ArrayList<>();


    // subscribe to the spring-cloud-demo topic and log the results
    @Bean
    public java.util.function.Consumer<String> receive() {
        return log::info;
    }

    // subscribe to the spring-cloud-demo-topic and send all incoming events to the grpc clients
    @Bean
    public java.util.function.Consumer<ChatEvent> handle() {
        return event -> {
            log.info("recieved a new event: {}", event.getType());
            // update all observers
            observers.forEach(observer -> {
                ChatResponse res = ChatResponse.newBuilder()
                        .setMessage(event.getPayload().getMessage())
                        .build();

                // send message with gRPC
                observer.onNext(res);
            });
        };
    }

    // register observers
    // gRPC
    @Override
    public void initialize(ConnectionInitRequest req, StreamObserver<ChatResponse> responseObserver) {
        observers.add(responseObserver);

        responseObserver.onNext(
                ChatResponse.newBuilder().setMessage("Ping").build()
        );
        // Client will send ConnectionInitRequest to establish a connection
        // The server can push ChatResponses to the client with responseObserver.onNext()
        // The connection closes with responseObserver.onCompleted();
    }
}
