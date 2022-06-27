package ao.co.tistech.exam.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity(name = "room")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_room_name", columnNames =  "name" )})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
