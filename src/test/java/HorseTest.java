import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {

    @Test
    public void whenCalledHorse_WithNullName_ThrowsIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 3, 15)
        );

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @Test
    public void whenCalledHorse_WithNullName_ThrowsIllegalArgumentException_Message() {

       try {
           new Horse(null, 3, 15);
           fail();
       } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t\t\t", "\n\n\n", "\r", "\f"})
    public void whenCalledHorse_WithBlankName_ThrowsIllegalArgumentException(String name) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 3, 15)
        );

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void whenCalledHorse_WithNegativeSpeed_ThrowsIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Cherry", -2, 15)
        );

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void whenCalledHorse_WithNegativeDistance_ThrowsIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Cherry", 2, -15)
        );

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void whenCalledGetName_ThenReturnString_Success() {

        Horse horse = new Horse("Blaze", 3, 15);
        String actual = horse.getName();

        assertEquals("Blaze", actual);
    }

    @Test
    public void whenCalledGetSpeed_ThenReturnNumber_Success() {

        Horse horse = new Horse("Blaze", 3.6, 15);
        double actual = horse.getSpeed();

        assertEquals(3.6, actual);
    }

    @Test
    public void whenCalledGetDistance_ThenReturnNumber_Success() {

        Horse horse = new Horse("Blaze", 3.6, 15.8);
        double actual = horse.getDistance();

        assertEquals(15.8, actual);
    }

    @Test
    public void whenCalledGetDistance_ThenReturnZero_Success() {

        Horse horse = new Horse("Blaze", 3.6);
        double actual = horse.getDistance();

        assertEquals(0, actual);
    }

    @Test
    void whenCalledMove_ThenReturnCalledGetRandomDouble_Success1() {

        try (MockedStatic<Horse> mockedStatic =  mockStatic(Horse.class)) {
            new Horse("Pegasus", 5.6, 28.6).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

            // Если один из аргументов конкретный, а второй - любой, то необходимо использовать следующее выражение:
            // mockedStatic.verify(() -> Horse.getRandomDouble(eq(0.2), anyDouble()));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.5, 0.7, 0.6, 0.4})
    void whenCalledMove_ThenReturnCalledGetRandomDouble_Success2(double random) {

        try (MockedStatic<Horse> mockedStatic =  mockStatic(Horse.class)) {
            Horse horse = new Horse("Pegasus", 5.6, 28.6);
            // Добавляем правило
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            // Проверяем, что ожидаемый результат дистанции совпадает с актуальным результатом,
            // то есть, формула в методе move() дает нужный результат
            assertEquals(28.6 + 5.6 * random, horse.getDistance());
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

}