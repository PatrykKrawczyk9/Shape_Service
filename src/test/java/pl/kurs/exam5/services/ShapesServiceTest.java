package pl.kurs.exam5.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kurs.exam5.data.Circle;
import pl.kurs.exam5.data.Rectangle;
import pl.kurs.exam5.data.Shape;
import pl.kurs.exam5.data.Square;
import pl.kurs.exam5.exceptions.InvalidDataException;
import pl.kurs.exam5.exceptions.ShapeNotFoundException;
import pl.kurs.exam5.utils.ObjectMapperHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ShapesServiceTest {

    private List<Shape> shapes;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
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
        MockitoAnnotations.openMocks(this);
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
    public void exportToJson_ShouldInvokeWriteValue_WhenCalled() throws IOException, InvalidDataException {
        //given
        String path = "test_shapes.json";
        ObjectWriter mockWriter = mock(ObjectWriter.class);
        when(mapper.writerFor(any(TypeReference.class))).thenReturn(mockWriter);

        service.exportToJson(shapes, path);

        verify(mockWriter, times(1)).writeValue(any(File.class), eq(shapes));
    }

    @Test
    public void exportToJson_ShouldCorrectlySerializeShapesToJson() throws Exception {
        //given
        String path = "test_shapes.json";

        ObjectMapper realMapper = ObjectMapperHolder.INSTANCE.getObjectMapper();
        String expectedJson = realMapper.writeValueAsString(shapes);

        ObjectWriter mockWriter = mock(ObjectWriter.class);

        ArgumentCaptor<List<Shape>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        when(mapper.writerFor(any(TypeReference.class))).thenReturn(mockWriter);

        //when
        service.exportToJson(shapes, path);

        //then
        verify(mockWriter).writeValue(any(File.class), argumentCaptor.capture());
        Object capturedArgument = argumentCaptor.getValue();
        String capturedJson = realMapper.writeValueAsString(capturedArgument);

        Assertions.assertThat(capturedJson).isEqualTo(expectedJson);
    }

    @Test
    public void exportToJson_ShouldThrowInvalidDataException_WhenIOExceptionOccurs() throws Exception {
        //given
        ObjectWriter mockWriter = mock(ObjectWriter.class);
        when(mapper.writerFor(any(TypeReference.class))).thenReturn(mockWriter);

        //when
        doThrow(IOException.class).when(mockWriter).writeValue(any(File.class), any());

        //then
        assertThrows(InvalidDataException.class, () -> service.exportToJson(new ArrayList<>(), "test_shapes.json"));
    }

    @Test
    public void importToJson_ShouldInvokeReadValue_WhenCalled() throws IOException, InvalidDataException {
        //given
        String path = "test_shapes.json";
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(shapes);

        service.importFromJson(path);

        verify(mapper, times(1)).readValue(any(File.class), any(TypeReference.class));
    }

    @Test
    public void importFromJson_ShouldReturnListOfShapes_WhenValidJsonProvided() throws IOException, InvalidDataException {
        //given
        String path = "test_shapes.json";
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(shapes);

        //when
        List<Shape> result = service.importFromJson(path);

        //then
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(shapes);
    }

    @Test
    public void importToJson_ShouldThrowInvalidDataException_WhenIOExceptionOccurs() throws Exception {
        //given
        when(mapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(IOException.class);

        //then
        assertThrows(InvalidDataException.class, () -> service.importFromJson("nieprew/test_shapes.json"));
    }

}