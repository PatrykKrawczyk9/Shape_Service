package pl.kurs.exam5.data;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class Circle implements Shape {

    private final double radius;

    @JsonCreator
    private Circle(@JsonProperty("radius") double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @JsonIgnore
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @JsonIgnore
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }

    public static class Factory {
        private static final Map<Double, Circle> cache = new HashMap<>();

        public static Circle createCircle(double radius) {
            return cache.computeIfAbsent(radius, Circle::new);
        }
    }
}
