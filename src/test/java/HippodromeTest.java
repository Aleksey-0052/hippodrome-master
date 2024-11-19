import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    public void whenCalledHippodrome_WithNullHorses_ThrowsIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void whenCalledHippodrome_WithEmptyHorses_ThrowsIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>())
        );

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void whenCalledGetHorses_ThenReturnList_Success() {

        List<Horse> expected = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            expected.add(new Horse("" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(expected);

        List<Horse> actual = hippodrome.getHorses();

        assertEquals(expected, actual);
    }

    @Test
    public void whenCalledMove_ThenReturnCalledMoveByEachHorse_Success() {

        List<Horse> horses = new ArrayList<>();
        // Добавили в список 50 моков лошадей
        for (int i = 1; i <= 50 ; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        // Проверили, что в результате вызова у объекта типа Hippodrome метода move() метод move() был вызван у 50
        // объектов класса Horse
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void whenCalledGetWinner_ThenReturnHorseWithMaxDistance_Success() {

        Horse horse1 = new Horse("Horse 1", 1, 15.28);
        Horse horse2 = new Horse("Horse 2", 1, 15.29);
        Horse horse3 = new Horse("Horse 3", 1, 8.67);
        Horse horse4 = new Horse("Horse 4", 1, 9.98);
        Horse horse5 = new Horse("Horse 5", 1, 10.79);
        List<Horse> horses = List.of(horse1, horse2, horse3, horse4, horse5);
        Hippodrome hippodrome = new Hippodrome(horses);

        Horse actual = hippodrome.getWinner();

        // Проверяем, что horse2 и actual - ссылаются на один и тот же объект
        assertSame(horse2, actual);
    }
}