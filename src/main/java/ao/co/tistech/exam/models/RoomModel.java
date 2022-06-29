package ao.co.tistech.exam.models;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "room")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_room_name", columnNames = "name")})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
public class RoomModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "roomSequence")
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;

    @OneToMany
    private List<AvailabilityModel> availabilities;
}
