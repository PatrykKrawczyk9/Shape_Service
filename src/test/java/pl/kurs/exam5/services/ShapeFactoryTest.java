package pl.kurs.exam5.services;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import pl.kurs.exam5.data.Circle;
import pl.kurs.exam5.data.Rectangle;
import pl.kurs.exam5.data.Square;
import pl.kurs.exam5.exceptions.InvalidDataException;

import static org.junit.Assert.*;

public class ShapeFactoryTest {

    @Test
    public void shouldCreateCircleWithValidRadius() throws InvalidDataException {
        //given
        Circle circle = ShapeFactory.createCircle(5);

        //then
        assertNotNull(circle);
        assertEquals(5, circle.getRadius(), 0.001);
    }

    @Test
    public void shouldReturnTheSameInstanceForTheSameRadiusValue() throws InvalidDataException {
        //given
        Circle circle1 = ShapeFactory.createCircle(10);
        Circle circle2 = ShapeFactory.createCircle(10);

        //then
        assertSame(circle1, circle2);
    }

    @Test
    public void shouldReturnNotSameInstanceForTheDifferentRadiusValue() throws InvalidDataException {
        //given
        Circle circle1 = ShapeFactory.createCircle(10);
        Circle circle2 = ShapeFactory.createCircle(20);

        //when
        assertNotSame(circle1, circle2);
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenRadiusIs0() {
        //given
        SoftAssertions sa = new SoftAssertions();

        //when
        Exception e = assertThrows(InvalidDataException.class, () -> ShapeFactory.createCircle(0));

        //then
        sa.assertThat(e).isExactlyInstanceOf(InvalidDataException.class);
        sa.assertThat(e).hasMessage("Nie można stworzyć koła o promieniu mniejszym lub równym 0");
        sa.assertAll();
    }

    @Test
    public void shouldCreateSquareWithValidSide() throws InvalidDataException {
        //given
        Square square = ShapeFactory.createSquare(10);

        //then
        assertNotNull(square);
        assertEquals(10, square.getSide(), 0.001);
    }

    @Test
    public void shouldReturnTheSameInstanceForTheSameSideValue() throws InvalidDataException {
        //given
        Square square1 = ShapeFactory.createSquare(5);
        Square square2 = ShapeFactory.createSquare(5);

        //when
        assertSame(square1, square2);
    }

    @Test
    public void shouldReturnNotSameInstanceForTheDifferentSideValue() throws InvalidDataException {
        //given
        Square square1 = ShapeFactory.createSquare(5);
        Square square2 = ShapeFactory.createSquare(10);

        //when
        assertNotSame(square1, square2);
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenSideIs0() {
        //given
        SoftAssertions sa = new SoftAssertions();

        //when
        Exception e = assertThrows(InvalidDataException.class, () -> ShapeFactory.createSquare(0));

        //then
        sa.assertThat(e).isExactlyInstanceOf(InvalidDataException.class);
        sa.assertThat(e).hasMessage("Nie można stworzyć kwadratu o boku mniejszym lub równym 0");
        sa.assertAll();
    }

    @Test
    public void shouldCreateRectangleWithValidSide() throws InvalidDataException {
        //given
        SoftAssertions sa = new SoftAssertions();
        Rectangle rectangle = ShapeFactory.createRectangle(2, 4);

        //then
        sa.assertThat(rectangle).isNotNull();
        sa.assertThat(rectangle.getLength()).isEqualTo(2);
        sa.assertThat(rectangle.getWidth()).isEqualTo(4);
        sa.assertAll();
    }

    @Test
    public void shouldReturnTheSameInstanceForTheSameLengthAndWidthValue() throws InvalidDataException {
        //given
        Rectangle rectangle1 = ShapeFactory.createRectangle(2, 4);
        Rectangle rectangle2 = ShapeFactory.createRectangle(2, 4);

        //then
        assertSame(rectangle1, rectangle2);
    }

    @Test
    public void shouldReturnNotSameInstanceForTheDifferentLengthAndWidthValue() throws InvalidDataException {
        //given
        Rectangle rectangle1 = ShapeFactory.createRectangle(2, 4);
        Rectangle rectangle2 = ShapeFactory.createRectangle(4, 5);

        //then
        assertNotSame(rectangle1, rectangle2);
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenLengthIs0() {
        //given
        SoftAssertions sa = new SoftAssertions();

        //when
        Exception e = assertThrows(InvalidDataException.class, () -> ShapeFactory.createRectangle(0, 2));

        //then
        sa.assertThat(e).isExactlyInstanceOf(InvalidDataException.class);
        sa.assertThat(e).hasMessage("Nie można stworzyć prostokątu o boku mniejszym lub równym 0");
        sa.assertAll();
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenWidthIs0() {
        //given
        SoftAssertions sa = new SoftAssertions();

        //when
        Exception e = assertThrows(InvalidDataException.class, () -> ShapeFactory.createRectangle(2, 0));

        //then
        sa.assertThat(e).isExactlyInstanceOf(InvalidDataException.class);
        sa.assertThat(e).hasMessage("Nie można stworzyć prostokątu o boku mniejszym lub równym 0");
        sa.assertAll();
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenLengthAndWidthIs0() {
        //given
        SoftAssertions sa = new SoftAssertions();

        //when
        Exception e = assertThrows(InvalidDataException.class, () -> ShapeFactory.createRectangle(0, 0));

        //then
        sa.assertThat(e).isExactlyInstanceOf(InvalidDataException.class);
        sa.assertThat(e).hasMessage("Nie można stworzyć prostokątu o boku mniejszym lub równym 0");
        sa.assertAll();
    }

}