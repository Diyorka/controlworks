package kg.inai.hippodrome.service;

import kg.inai.hippodrome.model.Horse;
import kg.inai.hippodrome.repository.HorseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorseService {
    private final HorseRepository horseRepository;

    public List<Horse> getAllHorses(){
        return horseRepository.findAll();
    }

    public ResponseEntity<String> addHorse(Horse horse){
        horseRepository.save(horse);
        return ResponseEntity.ok("Лошадь добавлена");
    }


}
