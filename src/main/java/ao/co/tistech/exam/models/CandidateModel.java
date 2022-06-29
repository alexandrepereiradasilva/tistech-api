package ao.co.tistech.exam.models;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "candidate")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_candidate_name", columnNames = "name")})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
public class CandidateModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "candidateSequence")
    private Long id;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @Column
    private String email;
}