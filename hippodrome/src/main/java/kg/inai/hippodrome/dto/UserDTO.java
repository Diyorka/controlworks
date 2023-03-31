package kg.inai.hippodrome.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
