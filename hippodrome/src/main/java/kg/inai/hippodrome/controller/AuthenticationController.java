package kg.inai.hippodrome.controller;

import jakarta.validation.Valid;
import kg.inai.hippodrome.dto.AuthUserDto;
import kg.inai.hippodrome.dto.AuthenticationResponse;
import kg.inai.hippodrome.dto.UserDTO;
import kg.inai.hippodrome.exception.UserAlreadyExistException;
import kg.inai.hippodrome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody UserDTO request
    ) throws UserAlreadyExistException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthUserDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }




}
