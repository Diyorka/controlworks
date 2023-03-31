package kg.inai.votingsystem.service;

import kg.inai.votingsystem.model.Candidate;
import kg.inai.votingsystem.model.User;
import kg.inai.votingsystem.model.Vote;
import kg.inai.votingsystem.model.Voting;
import kg.inai.votingsystem.repository.CandidateRepository;
import kg.inai.votingsystem.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    private final CandidateRepository candidateRepository;

    public ResponseEntity<String> addVoting(Voting voting, User creator) {
        voting.setCreator(creator);
        votingRepository.save(voting);
        return ResponseEntity.ok("Выборы добавлены!");
    }

    public List<Voting> getAllVotings(){
        return votingRepository.findAll();
    }

    public ResponseEntity<?> getVotingById(Long id) {
        if(votingRepository.existsById(id)) {
            return ResponseEntity.ok(votingRepository.findById(id).get());
        }else{
            return new ResponseEntity<>("Выборы с айди " + id + " не найдены!", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addCandidate(Long id, Long candidateId) {
        if(votingRepository.existsById(id)) {
            Voting voting = votingRepository.findById(id).get();
            List<Candidate> candidates = voting.getCandidates();
            if(candidateRepository.existsById(candidateId)) {
                candidates.add(candidateRepository.findById(candidateId).get());
                voting.setCandidates(candidates);
                votingRepository.save(voting);
                return ResponseEntity.ok("Кандидат добавлен!");
            }else {
                return new ResponseEntity<>("Кандидат с айди " + candidateId + " не найден", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("Выборы с айди " + id + " не найдены!", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getCandidatesByVotingId(Long id) {
        if(votingRepository.existsById(id)) {
            return ResponseEntity.ok(votingRepository.findById(id).get().getCandidates());
        }else{
            return new ResponseEntity<>("Выборы с id " + id + " не найдены!", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> addVote(Long id, User user, Long candidate_id) {

        if(votingRepository.existsById(id)){
            Voting voting = votingRepository.findById(id).get();
            List<Vote> votes = voting.getVotes();
            for (Vote vote : votes) {
                if (Objects.equals(vote.getUser().getId(), user.getId())) {
                    return new ResponseEntity<>("Пользователь уже проголосовал!", HttpStatus.BAD_REQUEST);
                }
            }

            if(candidateRepository.existsById(id)){
                votes.add(Vote.builder()
                        .candidate(candidateRepository.findById(candidate_id).get())
                        .user(user)
                        .build());
                voting.setVotes(votes);
                votingRepository.save(voting);
                return ResponseEntity.ok("Голос успешно добавлен!");
            }else{
                return new ResponseEntity<>("Кандидат с айди " + id + " не найден", HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity<>("Выборы с айди " + id + " не найдены", HttpStatus.BAD_REQUEST);
        }
    }

    public Object isCreator(Voting voting, User user) {
        return Objects.equals(voting.getCreator().getId(), user.getId());
    }
}
