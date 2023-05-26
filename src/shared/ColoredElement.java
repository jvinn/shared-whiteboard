package shared;

import java.awt.*;
import java.io.Serializable;

public class ColoredElement<T> implements Serializable {
    private final T element;
    private final Color color;

    public ColoredElement(T element, Color color) {
        this.element = element;
        this.color = color;
    }

    public T getElement() {
        return element;
    }

    public Color getColor() {
        return color;
    }
}
