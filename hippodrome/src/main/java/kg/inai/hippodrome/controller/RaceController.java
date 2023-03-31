package kg.inai.hippodrome.controller;

import kg.inai.hippodrome.model.Race;
import kg.inai.hippodrome.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/races")
public class RaceController {
    private final RaceService raceService;

    @GetMapping()
    public List<Race> getAllRaces(){
        return raceService.getAllRaces();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRaceById(@PathVariable("id") Long id){
        return raceService.getRaceById(id);
    }

    @GetMapping("/{id}/horses")
    public ResponseEntity<?> getCandidatesByVotingId(@PathVariable("id") Long id){
        return raceService.getHorsesByRaceId(id);
    }


    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addRace(@RequestBody Race race){
        return raceService.addRace(race);
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<?> getResults(@PathVariable("id") Long id){
        return raceService.getResults(id);
    }

    @GetMapping("/{id}/addHorse/{horse-id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addCandidate(@PathVariable("id") Long id, @PathVariable("horse-id") Long horseId){
        return raceService.addHorse(id, horseId);
    }
}
