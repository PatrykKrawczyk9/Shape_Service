package pl.kurs.exam5.data;

import java.util.Objects;

public class DimensionKey {
    private final double length;
    private final double width;

    public DimensionKey(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimensionKey that = (DimensionKey) o;
        return Double.compare(that.length, length) == 0 && Double.compare(that.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, width);
    }
}
