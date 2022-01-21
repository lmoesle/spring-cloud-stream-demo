package de.springclouddemo.event;

import de.springclouddemo.pojo.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ChatEvent implements Serializable {

    private final String type = "ChatEvent";
    private ChatMessage payload;

    public ChatEvent(ChatMessage msg) {
        this.payload = msg;
    }

}
