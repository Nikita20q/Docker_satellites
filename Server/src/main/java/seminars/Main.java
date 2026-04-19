package seminars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import seminars.exeptions.SpaceOperationException;
import seminars.factory.CommunicationSatelliteFactory;
import seminars.factory.ImagingSatelliteFactory;
import seminars.params.CommunicationSatelliteParam;
import seminars.params.ImagingSatelliteParam;
import seminars.repository.ConstellationRepository;
import seminars.services.SatelliteServiceImpl;
import seminars.services.ConstellationService;


@SpringBootApplication
public class Main{
    public static void main(String[] args) throws SpaceOperationException {

        System.out.println("============================================================");
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ");
        System.out.println("============================================================");

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ConstellationRepository constellationRepository = context.getBean(ConstellationRepository.class);
        ConstellationService constellationService = context.getBean(ConstellationService.class);

        ImagingSatelliteFactory imagingSatelliteFactory = context.getBean(ImagingSatelliteFactory.class);
        CommunicationSatelliteFactory communicationSatelliteFactory = context.getBean(CommunicationSatelliteFactory.class);
        SatelliteServiceImpl satelliteService = context.getBean(SatelliteServiceImpl.class);

        System.out.println("СОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:");
        System.out.println("---------------------------------------------");

        ImagingSatellite cS1 = (ImagingSatellite) satelliteService.createSatellite(new ImagingSatelliteParam("Связь-1", 100, 500.0));
        ImagingSatellite cS2 = (ImagingSatellite) satelliteService.createSatellite(new ImagingSatelliteParam("Связь-2", 100, 1000.0));
        CommunicationSatellite iS1 = (CommunicationSatellite) satelliteService.createSatellite(new CommunicationSatelliteParam("ДЗЗ-1", 100, 500.0));
        CommunicationSatellite iS2 = (CommunicationSatellite) satelliteService.createSatellite(new CommunicationSatelliteParam("ДЗЗ-2", 100, 1000.0));
        CommunicationSatellite iS3 = (CommunicationSatellite) satelliteService.createSatellite(new CommunicationSatelliteParam("ДЗЗ-3", 15, 1500.0));

        constellationService.createAndSaveConstellation("Орбита-1");
        constellationService.createAndSaveConstellation("Орбита-2");
        System.out.println("ФОРМИРОВАНИЕ ГРУППИРОВКИ:");
        System.out.println("---------------------------------------------");
        constellationService.addSatelliteToConstellation("Орбита-1", cS1);
        constellationService.addSatelliteToConstellation("Орбита-1", iS1);
        constellationService.addSatelliteToConstellation("Орбита-1", iS2);

        constellationService.addSatelliteToConstellation("Орбита-2", cS2);
        constellationService.addSatelliteToConstellation("Орбита-2", iS3);
        System.out.println("-----------------------------------");
        System.out.println("АКТИВАЦИЯ СПУТНИКОВ:");
        System.out.println("-----------------------------------");
        constellationService.activateAllConstellation("Орбита-1");
        constellationService.activateAllConstellation("Орбита-2");

        constellationService.executeConstellationMission("Орбита-1");
        constellationService.executeConstellationMission("Орбита-2");

        constellationService.showConstellationStatus("Орбита-1");
        constellationService.showConstellationStatus("Орбита-2");
        System.out.println(constellationRepository.getAllConstellations().toString());
    }
}
