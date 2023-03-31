package kg.inai.votingsystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "vote_name", unique = true)
    String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "voting_candidate",
            joinColumns = @JoinColumn(name = "voting_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id", unique = true))
    List<Candidate> candidates;

    @JoinColumn(name = "vote_id")
    @OneToMany(cascade = CascadeType.ALL)
    List<Vote> votes;


    @Column(name = "rec_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    Date creationTime;

    @ManyToOne(cascade = CascadeType.ALL)
    User creator;
}
