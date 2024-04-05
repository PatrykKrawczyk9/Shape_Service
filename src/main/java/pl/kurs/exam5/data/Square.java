package pl.kurs.exam5.data;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonTypeName("square")
public class Square implements Shape {
    private final double side;

    @JsonCreator
    private Square(@JsonProperty("side") double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    @JsonIgnore
    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @JsonIgnore
    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public String toString() {
        return "Square{" +
                "side=" + side +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Double.compare(square.side, side) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side);
    }

    public static class Factory {
        private static final Map<Double, Square> cache = new HashMap<>();

        public static Square createSquare(double side) {
            return cache.computeIfAbsent(side, Square::new);
        }
    }
}
