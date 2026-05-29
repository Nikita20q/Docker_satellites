package seminars.kafka;

import lombok.experimental.UtilityClass;
import seminars.Satellite;

import java.time.Instant;

@UtilityClass
public class KafkaUtils {
    public static SatelliteEvent createSatelliteEvent(Satellite satellite, SatelliteEvent.EventType eventType) {
        return new SatelliteEvent(satellite.getId(), satellite.getName(), eventType, Instant.now());
    }
}
