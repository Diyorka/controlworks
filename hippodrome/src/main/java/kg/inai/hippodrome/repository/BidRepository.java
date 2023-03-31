package kg.inai.hippodrome.repository;

import kg.inai.hippodrome.model.Bid;
import kg.inai.hippodrome.model.Horse;
import kg.inai.hippodrome.model.Race;
import kg.inai.hippodrome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByUser(User user);
    List<Bid> findAllByRace(Race race);

    List<Bid> findAllByRaceAndHorse(Race race, Horse horse);
}
