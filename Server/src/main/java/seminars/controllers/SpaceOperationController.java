package seminars.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seminars.domain.requests.AddSatelliteRequest;
import seminars.domain.requests.MissionRequest;
import seminars.exeptions.SpaceOperationException;
import seminars.services.SpaceOperationCenterService;

@RestController
@RequestMapping("api")
public class SpaceOperationController {
    private final SpaceOperationCenterService spaceOperationCenterService;

    public SpaceOperationController(SpaceOperationCenterService spaceOperationCenterService) {
        this.spaceOperationCenterService = spaceOperationCenterService;
    }

    @PostMapping("/missions")
    public ResponseEntity<Void> executeMission(@RequestBody MissionRequest request) {
        spaceOperationCenterService.executeMission(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-satellites")
    public ResponseEntity<Void> addSatellite(@RequestBody AddSatelliteRequest request) throws SpaceOperationException {
        spaceOperationCenterService.addSatellite(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/overview")
    public ResponseEntity<String> getOverview() {
        String overview = spaceOperationCenterService.getSystemOverview();
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(overview);
    }

    @DeleteMapping("/constellations/{constellationName}/satellites/{satelliteName}")
    public ResponseEntity<Void> decommissionSatellite(
            @PathVariable String constellationName,
            @PathVariable String satelliteName) throws  SpaceOperationException {

        spaceOperationCenterService.decommissionSatellite(constellationName, satelliteName);
        return ResponseEntity.noContent().build();
    }
}
