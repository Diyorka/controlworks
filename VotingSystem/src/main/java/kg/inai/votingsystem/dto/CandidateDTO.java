package kg.inai.votingsystem.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateDTO {
    Long id;

    String firstName;

    String lastName;

    Long voteCount;
}
