package kg.inai.votingsystem.dto;


import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @Size(min = 4, message = "Имя пользователя должно содержать минимум 4 символа")
    String username;

    @Size(min = 4, message = "Пароль должен содержать минимум 4 символа")
    String password;

    @NotEmpty(message = "Почта не может быть пустой")
    @Email(message = "Почта должна быть валидной")
    String email;

    String firstName;

    String lastName;

    String phoneNumber;

    String address;
}
