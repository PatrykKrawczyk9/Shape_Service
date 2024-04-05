package pl.kurs.exam5.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.kurs.exam5.data.Circle;
import pl.kurs.exam5.data.Rectangle;
import pl.kurs.exam5.data.Shape;
import pl.kurs.exam5.data.Square;

import java.io.IOException;

public class ShapeSerializer extends StdSerializer<Shape> {

    public ShapeSerializer() {
        super(Shape.class);
    }

    @Override
    public void serialize(Shape shape, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", shape.getClass().getSimpleName());
        switch (shape.getClass().getSimpleName()) {
            case "Square" -> {
                jsonGenerator.writeNumberField("side", ((Square) shape).getSide());
            }
            case "Circle" -> {
                jsonGenerator.writeNumberField("radius", ((Circle) shape).getRadius());
            }
            case "Rectangle" -> {
                jsonGenerator.writeNumberField("length", ((Rectangle) shape).getLength());
                jsonGenerator.writeNumberField("width", ((Rectangle) shape).getWidth());
            }
            default -> throw new JsonGenerationException("Nieobsługiwany typ obiektu: " + shape.getClass().getSimpleName(), jsonGenerator);
        }
        jsonGenerator.writeEndObject();
    }


}
