package kg.inai.hippodrome.controller;

import kg.inai.hippodrome.model.Horse;
import kg.inai.hippodrome.service.HorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/horses")
public class HorseController {
    private final HorseService horseService;

    @GetMapping()
    public List<Horse> getAllHorses(){
        return horseService.getAllHorses();
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addHorse(@RequestBody Horse horse){
        return horseService.addHorse(horse);
    }
}
