package seminars.services;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import seminars.Telemetry;
import seminars.TelemetryServiceGrpc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@GrpcService
@RequiredArgsConstructor
public class TelemetryServiceGrpcImpl extends TelemetryServiceGrpc.TelemetryServiceImplBase {
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private final Random random = new Random();
    private final SatelliteRepository satelliteRepository;

    @Override
    public void streamTelemetry(Telemetry.TelemetryRequest request, StreamObserver<Telemetry.TelemetryUpdate> responseObserver) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                Set<Long> activeIds = satelliteRepository.getAll();
                if (activeIds.isEmpty()) {
                    return;
                }

                List<Long> idList = new ArrayList<>(activeIds);
                Long satelliteId = idList.get(random.nextInt(idList.size()));

                Telemetry.TelemetryUpdate update = Telemetry.TelemetryUpdate.newBuilder()
                        .setSatelliteId(satelliteId)
                        .setTemperatureInside(20.0 + random.nextDouble() * 10)
                        .setTemperatureOutside(-50.0 + random.nextDouble() * 30)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
                responseObserver.onNext(update);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }, 0, 20, TimeUnit.SECONDS);
    }
}
