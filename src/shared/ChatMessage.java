package shared;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String username;
    private String content;

    public ChatMessage(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
