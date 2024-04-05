package pl.kurs.exam5.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kurs.exam5.data.Shape;
import pl.kurs.exam5.exceptions.InvalidDataException;
import pl.kurs.exam5.exceptions.ShapeNotFoundException;
import pl.kurs.exam5.utils.ObjectMapperHolder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ShapesService {

    private ObjectMapper mapper;

    public ShapesService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static Shape getShapeWithBiggestArea(List<Shape> shapes) throws ShapeNotFoundException {
        return Optional.ofNullable(shapes)
                .orElseGet(Collections::emptyList)
                .stream()
                .max(Comparator.comparing(Shape::getArea))
                .orElseThrow(() -> new ShapeNotFoundException("Nie znaleziono żadnego kształtu w przekazanej liście"));
    }

    public static <T extends Shape> T getShapeWithBiggestPerimeter(List<T> shapes, Class<? extends T> type) throws ShapeNotFoundException {
        return Optional.ofNullable(shapes)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(type::isInstance)
                .max(Comparator.comparing(Shape::getPerimeter))
                .orElseThrow(() -> new ShapeNotFoundException("Nie znaleziono żadnego kształtu w przekazanej liście"));
    }

    public void exportToJson(List<Shape> shapes, String path) throws InvalidDataException {
        File file = new File(path);

        try {
            mapper.writerFor(new TypeReference<List<Shape>>() {}).writeValue(file, shapes);
        } catch (IOException e) {
            String errorMessage = String.format("Nie można zapisać danych do pliku: %s. Powód: %s",
                    file.getAbsolutePath(), e.getMessage());
            throw new InvalidDataException(errorMessage, e);
        }
    }

    public List<Shape> importFromJson (String path) throws InvalidDataException {
        File file = new File(path);

        try {
            return mapper.readValue(file, new TypeReference<List<Shape>>(){});
        } catch (IOException e) {
            String errorMessage = String.format("Nie można odczytać danych z pliku: %s. Powód: %s",
                    file.getAbsolutePath(), e.getMessage());
            throw new InvalidDataException(errorMessage, e);
        }
    }

}
