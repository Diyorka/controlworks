package kg.inai.hippodrome.service;

import kg.inai.hippodrome.dto.AuthUserDto;
import kg.inai.hippodrome.dto.AuthenticationResponse;
import kg.inai.hippodrome.dto.UserDTO;
import kg.inai.hippodrome.exception.UserAlreadyExistException;
import kg.inai.hippodrome.model.Role;
import kg.inai.hippodrome.model.User;
import kg.inai.hippodrome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User getUserByPrinciple(Object principal) {
        if(principal == null) return new User();
        return repository.findByEmail(((User)principal).getUsername()).get();
    }

    public AuthenticationResponse register(UserDTO request) throws UserAlreadyExistException {
        if(repository.existsByEmail(request.getEmail()))
            throw new UserAlreadyExistException(
                    "email",
                    "Пользователь с такой почтой уже существует"
            );
        if(repository.existsByUsername(request.getUsername()))
            throw new UserAlreadyExistException(
                    "username",
                    "Пользователь с таким username уже существует"
            );
        var user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .moneyCount(50)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthUserDto request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public int getMyBalance(User user) {
        return user.getMoneyCount();
    }

    public ResponseEntity<String> addMoney(int moneyCount, User user) {
        user.setMoneyCount(user.getMoneyCount()+moneyCount);
        repository.save(user);
        return ResponseEntity.ok("Баланс пополнен!");
    }
}
