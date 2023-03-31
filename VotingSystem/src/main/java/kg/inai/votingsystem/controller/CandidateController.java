package kg.inai.votingsystem.controller;

import kg.inai.votingsystem.model.Candidate;
import kg.inai.votingsystem.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @GetMapping()
    public List<Candidate> getAllCandidates(){
        return candidateService.getAllCandidates();
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addCandidate(@RequestBody Candidate candidate){
        return candidateService.addCandidate(candidate);
    }
}
