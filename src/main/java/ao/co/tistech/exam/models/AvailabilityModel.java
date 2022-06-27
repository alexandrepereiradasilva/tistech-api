package ao.co.tistech.exam.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "availability")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_availability_name", columnNames = "name")})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "availabilitySequence")
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String name;
}
