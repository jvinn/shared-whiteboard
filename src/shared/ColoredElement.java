package shared;

import java.awt.*;
import java.io.Serializable;

public record ColoredElement<T>(T element, Color color) implements Serializable {
}
