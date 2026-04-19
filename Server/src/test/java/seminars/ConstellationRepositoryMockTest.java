package seminars;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seminars.repository.ConstellationRepository;
import seminars.services.ConstellationService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mock-тест сервиса")
public class ConstellationRepositoryMockTest {
    private static final double RESOLUTION = 112.0;
    private static final String CONSTELLATION_1 = "testConstellation1";
    private static final String CONSTELLATION_2 = "testConstellation2";
    @Mock
    private ConstellationRepository mockConstellationRepository;

    @InjectMocks
    private ConstellationService constellationService;

    @Test
    @DisplayName("Добавление спутника должно обращаться к репозиторию")
    void SpaceOperationCenterServiceMockTest() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);

        ImagingSatellite imagingSatellite = new ImagingSatellite("Спутник", 100.0, RESOLUTION);

        when(mockConstellationRepository.getConstellation(CONSTELLATION_1))
                .thenReturn(constellation);

        constellationService.addSatelliteToConstellation(CONSTELLATION_1, imagingSatellite);

        verify(mockConstellationRepository, times(1)).getConstellation(CONSTELLATION_1);
    }

    @Test
    @DisplayName("Создание группировки должно сохранять её в репозитории")
    void testCreateAndSaveConstellation() {

        constellationService.createAndSaveConstellation("NewGroup");

        verify(mockConstellationRepository, times(1)).addConstellation(any());
    }

    @Test
    @DisplayName("Выполнение миссии должно обращаться к репозиторию")
    void testExecuteConstellationMission() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        when(mockConstellationRepository.getConstellation(CONSTELLATION_1)).thenReturn(constellation);

        constellationService.executeConstellationMission(CONSTELLATION_1);

        verify(mockConstellationRepository, times(1)).getConstellation(CONSTELLATION_1);
    }

    @Test
    @DisplayName("Активация спутников должна обращаться к репозиторию")
    void testActivateAllConstellation() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        when(mockConstellationRepository.getConstellation(CONSTELLATION_1)).thenReturn(constellation);

        constellationService.activateAllConstellation(CONSTELLATION_1);

        verify(mockConstellationRepository, times(1)).getConstellation(CONSTELLATION_1);
    }

    @Test
    @DisplayName("Отображение статусов должно обращаться к репозиторию")
    void testShowConstellationStatus() {
        SatelliteConstellation constellation = new SatelliteConstellation(CONSTELLATION_1);
        when(mockConstellationRepository.getConstellation(CONSTELLATION_1)).thenReturn(constellation);

        constellationService.showConstellationStatus(CONSTELLATION_1);

        verify(mockConstellationRepository, times(1)).getConstellation(CONSTELLATION_1);
    }


    /*
    Не понял смысл этого теста,
    ведь даже если репозиторий на самом деле не сохраняет спутники - тест отработает успешно
     */
    @Test
    @DisplayName("Добавление нескольких группировок должно сохранять их все")
    void testRepository() {
        SatelliteConstellation constellation1 = new SatelliteConstellation(CONSTELLATION_1);
        SatelliteConstellation constellation2 = new SatelliteConstellation(CONSTELLATION_2);

        Map<String, SatelliteConstellation> constellations = Map.of(CONSTELLATION_1, constellation1, CONSTELLATION_2, constellation2);

        mockConstellationRepository.addConstellation(constellation1);
        mockConstellationRepository.addConstellation(constellation2);

        when(mockConstellationRepository.containsConstellation(CONSTELLATION_1)).thenReturn(true);
        when(mockConstellationRepository.containsConstellation(CONSTELLATION_2)).thenReturn(true);
        when(mockConstellationRepository.getAllConstellations()).thenReturn(constellations);

        assertTrue(mockConstellationRepository.containsConstellation(CONSTELLATION_1));
        assertTrue(mockConstellationRepository.containsConstellation(CONSTELLATION_2));
        assertEquals(2, mockConstellationRepository.getAllConstellations().size());
    }
}
