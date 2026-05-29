package seminars.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaService {
    private final KafkaTemplate<String, SatelliteEvent> kafkaTemplate;

    public void sendToKafkaSatellite(String topic, SatelliteEvent satelliteEvent) {
        kafkaTemplate.send(topic, String.valueOf(satelliteEvent.satelliteId()), satelliteEvent);
        log.info("Satellite event sent to topic {}", topic);
    }
}
