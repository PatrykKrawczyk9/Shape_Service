package pl.kurs.exam5.services;

import pl.kurs.exam5.data.Circle;
import pl.kurs.exam5.data.Rectangle;
import pl.kurs.exam5.data.Square;
import pl.kurs.exam5.exceptions.InvalidDataException;

public class ShapeFactory {

    public static Square createSquare(double side) throws InvalidDataException {
        if (side <= 0) {
            throw new InvalidDataException("Nie można stworzyć kwadratu o boku mniejszym lub równym 0");
        }
        return Square.Factory.createSquare(side);
    }

    public static Circle createCircle(double radius) throws InvalidDataException {
        if (radius <= 0) {
            throw new InvalidDataException("Nie można stworzyć koła o promieniu mniejszym lub równym 0");
        }
        return Circle.Factory.createCircle(radius);
    }

    public static Rectangle createRectangle(double length, double width) throws InvalidDataException {
        if (length <= 0 || width <= 0) {
            throw new InvalidDataException("Nie można stworzyć prostokątu o boku mniejszym lub równym 0");
        }
        return Rectangle.Factory.createRectangle(length, width);
    }
}
