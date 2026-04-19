package seminars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seminars.repository.ConstellationRepository;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Unit-тест репозитория")
class ConstellationRepositoryUnitTest {
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";
    private ConstellationRepository repository;

    @BeforeEach
    void setup() {
        repository = new ConstellationRepository();
    }

    @Test
    @DisplayName("Добавление нескольких группировок должно сохранять их все")
    void testRepository() {
        SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
        SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

        repository.addConstellation(constellation1);
        repository.addConstellation(constellation2);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
        assertTrue(repository.containsConstellation(CONSTELLATION_2));
        assertEquals(2, repository.getAllConstellations().size());

    }

    @Test
    @DisplayName("Получение существующей группировки должно возвращать объект")
    void testGetExistingConstellation() {
        SatelliteConstellation expected = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(expected);

        SatelliteConstellation actual = repository.getConstellation(CONSTELLATION_1);

        assertEquals(CONSTELLATION_1, actual.getConstellationName());
    }

    @Test
    @DisplayName("Получение несуществующей группировки должно выбрасывать RuntimeException")
    void testGetNonExistentConstellation() {
        assertThrows(RuntimeException.class, () ->
                repository.getConstellation("NonExistent")
        );
    }

    @Test
    @DisplayName("Удаление группировки должно удалять её из хранилища")
    void testRemoveConstellation() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(constellation);

        repository.removeConstellation(CONSTELLATION_1);

        assertFalse(repository.containsConstellation(CONSTELLATION_1));
        assertEquals(0, repository.getAllConstellations().size());
    }

    @Test
    @DisplayName("Обновление существующей группировки должно заменять данные")
    void testUpdateExistingConstellation() {
        SatelliteConstellation original = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(original);

        SatelliteConstellation updated = new SatelliteConstellation(CONSTELLATION_1);
        repository.updateConstellation(updated);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
        assertEquals(1, repository.getAllConstellations().size());
    }

    @Test
    @DisplayName("Обновление несуществующей группировки должно выбрасывать IllegalArgumentException")
    void testUpdateNonExistentConstellation() {
        SatelliteConstellation updated = new SatelliteConstellation("NonExistent");

        assertThrows(IllegalArgumentException.class, () ->
                repository.updateConstellation(updated)
        );
    }

    @Test
    @DisplayName("containsConstellation должен возвращать false для несуществующей группировки")
    void testContainsNonExistentConstellation() {
        assertFalse(repository.containsConstellation("NonExistent"));
    }

    @Test
    @DisplayName("getAllConstellations должен возвращать копию коллекции")
    void testGetAllConstellationsReturnsCopy() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(constellation);

        var all = repository.getAllConstellations();
        all.remove(CONSTELLATION_1);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
    }

    @Test
    @DisplayName("Добавление группировки с пустым именем должно обрабатываться корректно")
    void testAddConstellationWithEmptyName() {
        SatelliteConstellation constellation = new SatelliteConstellation("");

        assertDoesNotThrow(() -> repository.addConstellation(constellation));
    }

    @Test
    @DisplayName("Получение группировки по null должно выбрасывать исключение")
    void testGetConstellationWithNull() {
        assertThrows(Exception.class, () -> repository.getConstellation(null));
    }
}
