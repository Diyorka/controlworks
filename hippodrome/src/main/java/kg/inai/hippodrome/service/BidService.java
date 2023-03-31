package kg.inai.hippodrome.service;

import kg.inai.hippodrome.model.Bid;
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

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final RaceRepository raceRepository;
    private final UserRepository userRepository;
    private final HorseRepository horseRepository;

    public ResponseEntity<String> addBid(Long raceId, Long horseId, Bid addBid, User user) {

        if(user.getMoneyCount() < addBid.getBid()) {
            return new ResponseEntity<>("Недостаточно средств", HttpStatus.BAD_REQUEST);
        }

        if(raceRepository.existsById(raceId)){

            Race race = raceRepository.findById(raceId).get();

            boolean isHorseInRace = false;
            for (int i = 0; i < race.getHorses().size(); i++) {
                if(Objects.equals(horseId, race.getHorses().get(i).getId())){
                    isHorseInRace = true;
                }
            }
            if(isHorseInRace){
                user.setMoneyCount(user.getMoneyCount() - addBid.getBid());
                userRepository.save(user);

                Bid bid = Bid.builder()
                        .bid(addBid.getBid())
                        .user(user)
                        .race(race)
                        .horse(horseRepository.findById(horseId).get())
                        .build();

                bidRepository.save(bid);
                return ResponseEntity.ok("Ставка сделана!");

            }else{
                return new ResponseEntity<>("Лошадь не найдена", HttpStatus.NOT_FOUND);
            }

        }else{
            return new ResponseEntity<>("Скачка не найдена", HttpStatus.NOT_FOUND);
        }
    }

    public List<Bid> getAllBidsByUser(User user){
        return bidRepository.findAllByUser(user);
    }

    public List<Bid> getAllBids(){
        return bidRepository.findAll();
    }
}
