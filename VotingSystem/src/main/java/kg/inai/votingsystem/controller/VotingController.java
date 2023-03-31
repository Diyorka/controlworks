package kg.inai.votingsystem.controller;

import kg.inai.votingsystem.dto.CandidateDTO;
import kg.inai.votingsystem.model.Voting;
import kg.inai.votingsystem.service.CandidateService;
import kg.inai.votingsystem.service.UserService;
import kg.inai.votingsystem.service.VotingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votings")
public class VotingController {
    private final VotingService votingService;
    private final UserService userService;
    private final CandidateService candidateService;

    @GetMapping()
    public List<Voting> getAllVotings(){
        return votingService.getAllVotings();
    }

    @GetMapping("/{id}/candidates")
    public ResponseEntity<?> getCandidatesByVotingId(@PathVariable("id") Long id){
        return votingService.getCandidatesByVotingId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVotingById(@PathVariable("id") Long id){
        return votingService.getVotingById(id);
    }

    @GetMapping("/{id}/results")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CandidateDTO> getResults(@PathVariable("id") Long id){
        return candidateService.getCandidatesWithVotes(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addVoting(@RequestBody Voting voting){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return votingService.addVoting(voting, userService.getUserByPrinciple(principal));
    }

    @GetMapping("/{id}/addCandidate/{candidate-id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addCandidate(@PathVariable("id") Long id, @PathVariable("candidate-id") Long candidateId){
        return votingService.addCandidate(id, candidateId);
    }

    @GetMapping("/{id}/vote/{candidate-id}")
    public ResponseEntity<?> addVote(@PathVariable("id") Long id,
                                     @PathVariable("candidate-id") Long candidateId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return votingService.addVote(id, userService.getUserByPrinciple(principal), candidateId);
    }



}
