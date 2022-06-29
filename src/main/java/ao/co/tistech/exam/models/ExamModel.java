package ao.co.tistech.exam.models;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "exam")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_exam_name", columnNames = "name")})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
public class ExamModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "examSequence")
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "availability_id")
    private AvailabilityModel availability;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private CandidateModel candidate;
}
