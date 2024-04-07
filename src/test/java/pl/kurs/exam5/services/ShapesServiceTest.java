package pl.kurs.exam5.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import pl.kurs.exam5.data.Circle;
import pl.kurs.exam5.data.Rectangle;
import pl.kurs.exam5.data.Shape;
import pl.kurs.exam5.data.Square;
import pl.kurs.exam5.exceptions.InvalidDataException;
import pl.kurs.exam5.exceptions.ShapeNotFoundException;
import pl.kurs.exam5.utils.ObjectMapperHolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertSame;

public class ShapesServiceTest {

    private List<Shape> shapes;
    private ObjectMapper mapper;
    private ShapesService service;

    @Before
    public void init() throws InvalidDataException {
        shapes = Arrays.asList(
                ShapeFactory.createSquare(10),
                ShapeFactory.createSquare(6.5),
                ShapeFactory.createCircle(5.5),
                ShapeFactory.createCircle(7.5),
                ShapeFactory.createRectangle(10, 15),
                ShapeFactory.createRectangle(3.5, 2.5)
        );
        mapper = ObjectMapperHolder.INSTANCE.getObjectMapper();
        service = new ShapesService(mapper);
    }

    @Test
    public void shouldReturnRectangleWithBiggestArea() throws ShapeNotFoundException {
        //when
        Shape result = ShapesService.getShapeWithBiggestArea(shapes);

        //then
        assertSame(shapes.get(3), result);
    }

    @Test(expected = ShapeNotFoundException.class)
    public void getShapeWithBiggestArea_ShouldThrowInvalidDataException_WithNullList() throws ShapeNotFoundException {
        //given
        List<Shape> shapes = null;

        //when
        ShapesService.getShapeWithBiggestArea(shapes);
    }

    @Test(expected = ShapeNotFoundException.class)
    public void getShapeWithBiggestArea_ShouldThrowInvalidDataException_WithEmptyList() throws ShapeNotFoundException {
        //given
        List<Shape> shapes = Collections.emptyList();

        //then
        ShapesService.getShapeWithBiggestArea(shapes);
    }

    @Test
    public void shouldReturnCircleWithBiggestPerimeter() throws ShapeNotFoundException {
        //when
        Shape result = ShapesService.getShapeWithBiggestPerimeter(shapes, Circle.class);

        //then
        assertSame(shapes.get(3), result);
    }

    @Test(expected = ShapeNotFoundException.class)
    public void getShapeWithBiggestPerimeter_ShouldThrowInvalidDataException_WithNullList() throws ShapeNotFoundException {
        //given
        List<Shape> shapes = null;

        //then
        ShapesService.getShapeWithBiggestPerimeter(shapes, Rectangle.class);
    }

    @Test(expected = ShapeNotFoundException.class)
    public void getShapeWithBiggestPerimeter_ShouldThrowInvalidDataException_WithEmptyList() throws ShapeNotFoundException {
        //given
        List<Shape> shapes = Collections.emptyList();

        //then
        ShapesService.getShapeWithBiggestPerimeter(shapes, Square.class);
    }

    @Test
    public void exportToJson_ShouldCorrectlySerializeShapesToJson() throws InvalidDataException, ShapeNotFoundException, IOException {
        //given
        String path = "test_shapes.json";
        SoftAssertions sa = new SoftAssertions();

        //when
        service.exportToJson(shapes, path);

        //then
        File testFile = new File(path);
        sa.assertThat(testFile).exists();

        if(testFile.exists()) {
            List<Shape> deserializedShapes = mapper.readValue(new File(path), new TypeReference<List<Shape>>() {});
            sa.assertThat(deserializedShapes).containsExactlyElementsOf(shapes);
        }

        sa.assertAll();
        Files.delete(Path.of(path));
    }

    @Test(expected = ShapeNotFoundException.class)
    public void exportToJson_ShouldThrowShapeNotFoundException_WhenListIsEmpty() throws InvalidDataException, ShapeNotFoundException {
        //given
        String path = "test_shapes.json";
        List<Shape> shapeList = Collections.emptyList();

        //then
        service.exportToJson(shapeList, path);
    }

    @Test(expected = InvalidDataException.class)
    public void exportToJson_ShouldThrowInvalidDataException_WhenIOExceptionOccurs() throws InvalidDataException, ShapeNotFoundException {
        //given
        String invalidPath = "zlasciezka/test_shapes.json";

        //then
        service.exportToJson(shapes, invalidPath);
    }

    @Test
    public void importFromJson_ShouldReturnListOfShapes_WhenValidJsonProvided() throws InvalidDataException, IOException, ShapeNotFoundException {
        //given
        String path = "test_shapes_to_import.json";
        service.exportToJson(shapes, path);

        //when
        List<Shape> result = service.importFromJson(path);
        List<Shape> expectedShapes = mapper.readValue(new File(path), new TypeReference<List<Shape>>(){});

        //then
        Assertions.assertThat(result).containsExactlyElementsOf(expectedShapes);

        File file = new File(path);
        file.delete();
    }

    @Test(expected = InvalidDataException.class)
    public void importToJson_ShouldThrowInvalidDataException_WhenIOExceptionOccurs() throws InvalidDataException {
        //given
        String invalidPath = "zlasciezka/test_shapes.json";

        //then
        service.importFromJson(invalidPath);
    }

}