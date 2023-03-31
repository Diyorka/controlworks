package kg.inai.hippodrome.controller;

import kg.inai.hippodrome.model.User;
import kg.inai.hippodrome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/mybalance")
    public int getMyBalance(@AuthenticationPrincipal User user){
        return userService.getMyBalance(user);
    }

    @GetMapping("/addMoney/{moneyCount}")
    public ResponseEntity<String> addMoney(@PathVariable("moneyCount") int moneyCount,
                                           @AuthenticationPrincipal User user){
        return userService.addMoney(moneyCount, user);
    }
}
