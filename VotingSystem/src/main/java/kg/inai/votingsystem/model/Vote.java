package kg.inai.votingsystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name = "candidate_id")
    @ManyToOne
    Candidate candidate;


    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;

    @Column(name = "created_at")
    @CreationTimestamp
    Date creationTime;
}
