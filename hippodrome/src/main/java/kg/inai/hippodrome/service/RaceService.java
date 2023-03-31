package kg.inai.hippodrome.service;

import kg.inai.hippodrome.model.Bid;
import kg.inai.hippodrome.model.Horse;
import kg.inai.hippodrome.model.Race;
import kg.inai.hippodrome.model.User;
import kg.inai.hippodrome.repository.BidRepository;
import kg.inai.hippodrome.repository.HorseRepository;
import kg.inai.hippodrome.repository.RaceRepository;
import kg.inai.hippodrome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final RaceRepository raceRepository;
    private final BidRepository bidRepository;
    private final HorseRepository horseRepository;
    private final UserRepository userRepository;

    public List<Race> getAllRaces(){
        return raceRepository.findAll();
    }

    public ResponseEntity<?> getRaceById(Long id){
        if(raceRepository.existsById(id)){
            return ResponseEntity.ok(raceRepository.findById(id));
        }else{
            return new ResponseEntity<>("Скачка с айди " + id + " не найдена", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addRace(Race race){
        raceRepository.save(race);
        return ResponseEntity.ok("Скачки добавлены");
    }


    public ResponseEntity<?> getResults(Long id) {

        if(raceRepository.existsById(id)){
            Race race = raceRepository.findById(id).get();
            List<Bid> bids = bidRepository.findAllByRace(race);
            List<Horse> winners = new ArrayList<>();
            Random random = new Random();
            Long winnerId = (long) random.nextInt(7);
            winnerId+=1;

            int sum = bids.stream().mapToInt(Bid::getBid).sum();
            int bidsSum = 0;
            for (Bid bid:bids) {
                bidsSum+=bid.getBid();
            }
            List<Bid> winBids = bidRepository.findAllByRaceAndHorse(race, horseRepository.findById(winnerId).get());

            for (Bid bid:winBids) {
                int addSum = bidsSum/winBids.size();
                User user = userRepository.findById(bid.getUser().getId()).get();
                user.setMoneyCount(user.getMoneyCount()+addSum);
                userRepository.save(user);
            }
            return ResponseEntity.ok(winBids);
        }else{
            return new ResponseEntity<>("Скачка не найдена", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addHorse(Long id, Long horseId) {
        if(raceRepository.existsById(id)) {
            Race race = raceRepository.findById(id).get();
            List<Horse> horses = race.getHorses();
            if(horseRepository.existsById(horseId)) {
                horses.add(horseRepository.findById(horseId).get());
                race.setHorses(horses);
                raceRepository.save(race);
                return ResponseEntity.ok("Лошадь добавлена!");
            }else {
                return new ResponseEntity<>("Лошадь с айди " + horseId + " не найдена", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("Скачки с айди " + id + " не найдены!", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> getHorsesByRaceId(Long id) {
        if(raceRepository.existsById(id)) {
            return ResponseEntity.ok(raceRepository.findById(id).get().getHorses());
        }else{
            return new ResponseEntity<>("Скачки с id " + id + " не найдены!", HttpStatus.NOT_FOUND);
        }
    }
}
