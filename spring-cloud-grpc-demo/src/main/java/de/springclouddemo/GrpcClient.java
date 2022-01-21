package de.springclouddemo;

import de.springclouddemo.chat.ChatGrpc;
import de.springclouddemo.chat.ChatResponse;
import de.springclouddemo.chat.ConnectionInitRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcClient {

    public static void main(String[] args) {
        // create a channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        log.info("Start initializing");

        // create a blockingStub for the channel
        ChatGrpc.ChatBlockingStub blockingStub = blockingStub = ChatGrpc.newBlockingStub(channel);

        // create the ConnectionInitRequest
        ConnectionInitRequest req = ConnectionInitRequest.newBuilder().setMsg("Client tries initializing").build();
        ChatResponse response;
        try {
            // send the ConnectionInitRequest and log all incoming events
            var responses = blockingStub.initialize(req);
            while (responses.hasNext()) {
                log.info(responses.next().getMessage());
            }
        } catch (StatusRuntimeException e) {
            log.warn(e.getStatus().toString());
            log.warn("RPC failed: {0}", e.getStatus());
            return;
        }

        channel.shutdown();
    }

}
