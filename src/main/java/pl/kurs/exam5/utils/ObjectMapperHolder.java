package pl.kurs.exam5.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.module.SimpleModule;
import pl.kurs.exam5.data.Shape;

public enum ObjectMapperHolder {
    INSTANCE;

    private final ObjectMapper objectMapper;

    ObjectMapperHolder() {
        this.objectMapper = create();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule sm1 = new SimpleModule("Shape Serializer");
        ShapeSerializer shapeSerializer = new ShapeSerializer();
        sm1.addSerializer(shapeSerializer);

        SimpleModule sm2 = new SimpleModule("Shape Deserializer");
        ShapeDeserializer shapeDeserializer = new ShapeDeserializer();
        sm2.addDeserializer(Shape.class, shapeDeserializer);

        mapper.registerModules(sm1, sm2);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        return mapper;
    }
}

