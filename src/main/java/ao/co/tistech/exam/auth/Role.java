package ao.co.tistech.exam.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "roleTistech")
@Table(uniqueConstraints = {@UniqueConstraint(name = "uk_role_name", columnNames = {"name"})})
public class Role {

    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "role_sequence")
    private Long id;

    @NotNull(message = "Name cannot by null!")
    @NotEmpty(message = "Name cannot by empty!")
    @Size(max = 30)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<User> users;
}
