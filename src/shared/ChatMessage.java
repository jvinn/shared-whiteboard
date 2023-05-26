package shared;

import java.io.Serializable;

public record ChatMessage(String username, String content) implements Serializable {
}
