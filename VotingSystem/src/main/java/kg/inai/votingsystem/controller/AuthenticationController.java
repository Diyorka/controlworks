package kg.inai.votingsystem.controller;

import jakarta.validation.Valid;
import kg.inai.votingsystem.dto.AuthUserDto;
import kg.inai.votingsystem.dto.AuthenticationResponse;
import kg.inai.votingsystem.dto.UserDTO;
import kg.inai.votingsystem.exception.UserAlreadyExistException;
import kg.inai.votingsystem.service.UserService;
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
