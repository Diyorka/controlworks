package kg.inai.votingsystem.service;

import kg.inai.votingsystem.dto.CandidateDTO;
import kg.inai.votingsystem.model.Candidate;
import kg.inai.votingsystem.model.Voting;
import kg.inai.votingsystem.repository.CandidateRepository;
import kg.inai.votingsystem.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final VotingRepository votingRepository;


    public List<Candidate> getAllCandidates(){
        return candidateRepository.findAll();
    }

    public ResponseEntity<String> addCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
        return ResponseEntity.ok("Кандидат успешно добавлен");
    }

    public List<CandidateDTO> getCandidatesWithVotes(Long id) {

        Voting voting = votingRepository.findById(id).get();
        List<CandidateDTO> candidates = new ArrayList<>();
        for (int i = 0; i < voting.getCandidates().size(); i++) {
            candidates.add(CandidateDTO.builder()
                    .firstName(voting.getCandidates().get(i).getFirstName())
                    .lastName(voting.getCandidates().get(i).getLastName())
                    .id(voting.getCandidates().get(i).getId())
                    .voteCount(0L).build()
            );
        }

        for (int i = 0; i < voting.getVotes().size(); i++) {
            for (CandidateDTO candidate : candidates) {
                if (Objects.equals(voting.getVotes().get(i).getCandidate().getId(), candidate.getId())) {
                    candidate.setVoteCount(candidate.getVoteCount() + 1);
                };
            }
        }
        candidates.sort((o1, o2) -> {
            if (Objects.equals(o1.getVoteCount(), o2.getVoteCount())) return 0;
            if (o1.getVoteCount() > o2.getVoteCount())
                return -1;
            else return 1;
        });
        return candidates;
    }
}
