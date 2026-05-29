package seminars.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SatelliteRepository {
    private final Set<Long> satelliteIds = ConcurrentHashMap.newKeySet();

    public void add(Long satelliteId) {
        if (satelliteIds.add(satelliteId)) {
            log.info("Added Satellite with id {}", satelliteId);
        }
    }

    public void remove(Long satelliteId) {
        if (satelliteIds.remove(satelliteId)) {
            log.info("Removed Satellite with id {}", satelliteId);
        }
    }

    public Set<Long> getAll() {
        return Collections.unmodifiableSet(satelliteIds);
    }

    public boolean isEmpty() {
        return satelliteIds.isEmpty();
    }
}
