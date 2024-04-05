package pl.kurs.exam5.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import pl.kurs.exam5.data.Shape;
import pl.kurs.exam5.exceptions.InvalidDataException;
import pl.kurs.exam5.services.ShapeFactory;

import java.io.IOException;

public class ShapeDeserializer extends StdDeserializer<Shape> {

    public ShapeDeserializer() {
        super(Shape.class);
    }

    @Override
    public Shape deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String type = node.get("type").asText();

        try {
            switch (type) {
                case "Square" -> {
                    return ShapeFactory.createSquare(node.get("side").asDouble());
                }
                case "Circle" -> {
                    return ShapeFactory.createCircle(node.get("radius").asDouble());
                }
                case "Rectangle" -> {
                    return ShapeFactory.createRectangle(node.get("length").asDouble(), node.get("width").asDouble());
                }
                default -> throw new JsonMappingException(jsonParser, "Nieznany typ kształtu: " + type);
            }
        } catch (InvalidDataException e) {
            throw new InvalidFormatException(jsonParser, "Błąd podczas tworzenia kształtu: " + e.getMessage(), node, Shape.class);
        }
    }
}
