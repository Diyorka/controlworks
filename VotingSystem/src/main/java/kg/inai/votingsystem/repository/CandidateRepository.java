package kg.inai.votingsystem.repository;

import kg.inai.votingsystem.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByFirstName(String firstName);
}
