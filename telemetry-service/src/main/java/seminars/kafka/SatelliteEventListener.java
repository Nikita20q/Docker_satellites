package seminars.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import seminars.services.SatelliteRepository;

@RequiredArgsConstructor
@Slf4j
@Component
public class SatelliteEventListener {
    private static final String TOPIC = "satellite-events";
    private final SatelliteRepository satelliteRepository;

    @KafkaListener(topics = TOPIC, groupId = "telemetry-service-group")
    public void handleSatelliteEvent(ConsumerRecord<String, SatelliteEvent> record) {
        try {
            SatelliteEvent event = record.value();
            log.info("Received satellite event {}", event);
            switch (event.eventType()) {
                case CREATED -> satelliteRepository.add(event.satelliteId());
                case DELETED -> satelliteRepository.remove(event.satelliteId());
            }
        } catch (Exception e) {
            log.error("Error while processing Satellite event", e);
        }
    }

}
