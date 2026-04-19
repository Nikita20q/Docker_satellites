package seminars;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seminars.repository.ConstellationRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Integration-тест репозитория")
class ConstellationRepositoryIntegrationTest {
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";

    @Autowired
    private ConstellationRepository repository;

    @Test
    @DisplayName("Добавление нескольких группировок должно сохранять их все")
    void testRepository() {
        SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
        SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

//        Map<String, SatelliteConstellation> constellations = Map.of(CONSTELLATION_1, constellation1, CONSTELLATION_2, constellation2);

        repository.addConstellation(constellation1);
        repository.addConstellation(constellation2);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
        assertTrue(repository.containsConstellation(CONSTELLATION_2));
        assertEquals(2, repository.getAllConstellations().size());

    }

    @Test
    @DisplayName("Получение существующей группировки")
    void testGetExistingConstellation() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(constellation);

        SatelliteConstellation constellation1 = repository.getConstellation(CONSTELLATION_1);
        assertEquals(CONSTELLATION_1, constellation1.getConstellationName());
    }

    @Test
    @DisplayName("Получение несуществующей группировки")
    void testGetNonExistingConstellation() {
        assertThrows(RuntimeException.class, () ->
                repository.getConstellation("NonExistent")
        );
    }

    @Test
    @DisplayName("Обновление существующей группировки")
    void testUpdateConstellation() {
        SatelliteConstellation original = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(original);

        SatelliteConstellation updated = new SatelliteConstellation(CONSTELLATION_1);
        repository.updateConstellation(updated);

        assertTrue(repository.containsConstellation(CONSTELLATION_1));
    }

    @Test
    @DisplayName("Обновление несуществующей группировки должно выбрасывать исключение")
    void testUpdateNonExistentConstellation() {
        SatelliteConstellation updated = new SatelliteConstellation("Unknown");

        assertThrows(IllegalArgumentException.class, () ->
                repository.updateConstellation(updated)
        );
    }

    @Test
    @DisplayName("Удаление существующей группировки")
    void testDeleteConstellation() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        repository.addConstellation(constellation);

        repository.removeConstellation(CONSTELLATION_1);

        assertFalse(repository.containsConstellation(CONSTELLATION_1));
        assertEquals(0, repository.getAllConstellations().size());
    }

    @Test
    @DisplayName("Удаление несуществующей группировки")
    void testDeleteNonExistentConstellation() {
        SatelliteConstellation updated = new SatelliteConstellation("Unknown");

        assertThrows(IllegalArgumentException.class, () ->
                repository.updateConstellation(updated)
        );
    }
}
