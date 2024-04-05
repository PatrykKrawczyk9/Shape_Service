package pl.kurs.exam5.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Rectangle implements Shape {
    private final double length;
    private final double width;

    @JsonCreator
    private Rectangle(@JsonProperty("length") double length, @JsonProperty("width") double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    @JsonIgnore
    @Override
    public double getPerimeter() {
        return 2 * length + 2 * width;
    }

    @JsonIgnore
    @Override
    public double getArea() {
        return length * width;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "length=" + length +
                ", width=" + width +
                '}';
    }

    public static class Factory {
        private static final Map<DimensionKey, Rectangle> cache = new HashMap<>();

        public static Rectangle createRectangle(double length, double width) {
            DimensionKey key = new DimensionKey(length, width);
            return cache.computeIfAbsent(key, k -> new Rectangle(k.getLength(), k.getWidth()));
        }
    }
}
