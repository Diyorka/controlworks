package kg.inai.hippodrome.controller;

import kg.inai.hippodrome.model.Bid;
import kg.inai.hippodrome.model.User;
import kg.inai.hippodrome.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bids")
public class BidController {
    private final BidService bidService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Bid> getAllBids(){
        return bidService.getAllBids();
    }

    @GetMapping("/mybids")
    public List<Bid> getAllBidsByUser(@AuthenticationPrincipal User user){
        return bidService.getAllBidsByUser(user);
    }

    @PostMapping("/{race-id}/{horse-id}")
    public ResponseEntity<String> addBid(@PathVariable("race-id") Long raceId,
                                        @PathVariable("horse-id") Long horseId,
                                        @RequestBody Bid bid,
                                         @AuthenticationPrincipal User user){
        return bidService.addBid(raceId, horseId, bid, user);
    }
}
