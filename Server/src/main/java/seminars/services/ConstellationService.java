package seminars.services;

import org.springframework.stereotype.Service;
import seminars.Satellite;
import seminars.SatelliteConstellation;
import seminars.exeptions.SpaceOperationException;
import seminars.repository.ConstellationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class ConstellationService {
    private final ConstellationRepository constellationRepository;
    public ConstellationService(ConstellationRepository constellationRepository) {
        this.constellationRepository = constellationRepository;
    }
    public void addSatelliteToConstellation(String constellationName, Satellite satellite) {
        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        constellation.addSatellite(satellite);
        System.out.println("Добавлен спутник: " + satellite.getName() + " в группировку " + constellationName);
    }

    public void createAndSaveConstellation(String constellationName) {
        SatelliteConstellation constellation = new SatelliteConstellation(constellationName);
        constellationRepository.addConstellation(constellation);
    }
    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        System.out.println("\n===ВЫПОЛНЕНИЕ МИССИЙ ДЛЯ ГРУППИРОВКИ: " +  constellationName + "===");
        constellation.executeAllMission();
    }
    public void activateAllConstellation(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        System.out.println("\n=== АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ: " + constellationName + " ===");
        for  (Satellite satellite : constellation.getSatellites()) {
            satellite.activate();
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);
        System.out.println("\n=== СТАТУС ГРУППИРОВКИ:  " + constellationName + " ===");
        System.out.println("Количество спутников: " + constellation.getSatellites().size());
        for (Satellite satellite : constellation.getSatellites()) {
            System.out.println(satellite.getState());
        }
    }

    public boolean checkConstellationExists(String constellationName) {
        try {
            return constellationRepository.getConstellation(constellationName) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public SatelliteConstellation getConstellations(String constellationName) {
        return constellationRepository.getConstellation(constellationName);
    }

    public Collection<SatelliteConstellation> getAllConstellations() {
        return constellationRepository.getAllConstellations().values();
    }

    public void removeSatelliteFromConstellation(String constellationName, String satelliteName)
            throws SpaceOperationException {

        SatelliteConstellation constellation = constellationRepository.getConstellation(constellationName);

        Optional<Satellite> satelliteOpt = constellation.getSatellites().stream()
                .filter(s -> s.getName().equals(satelliteName))
                .findFirst();

        if (satelliteOpt.isEmpty()) {
            throw new SpaceOperationException(
                    String.format("Спутник '%s' не найден в группировке '%s'", satelliteName, constellationName));
        }

        constellation.getSatellites().remove(satelliteOpt.get());
        System.out.println("Спутник удалён: " + satelliteName);
    }
}
