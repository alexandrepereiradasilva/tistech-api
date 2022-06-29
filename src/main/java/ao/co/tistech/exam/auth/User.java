package ao.co.tistech.exam.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "userTistech")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_name", columnNames = {"name"}),
        @UniqueConstraint(name = "uk_user_email", columnNames = {"email"})})
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    private Long id;

    @Size(min = 2, max = 30, message = "Size name of user between 2 and 30!")
    @NotNull(message = "Name cannot by null!")
    @NotEmpty(message = "Name cannot by empty!")
    private String name;

    @Size(min = 2, max = 30, message = "Size last name of user between 2 and 30!")
    @NotNull(message = "Last name cannot by null!")
    @NotEmpty(message = "Last name cannot by empty!")
    private String lastName = "";

    @NotNull(message = "Email cannot by null!")
    @NotEmpty(message = "Email cannot by empty!")
    @Email(message = "Invalid format email!")
    @Size(max = 40)
    private String email;

    @Size(min = 2, max = 30, message = "Size user name of user between 2 and 30!")
    @NotNull(message = "User name cannot by null!")
    @NotEmpty(message = "User name cannot by empty!")
    private String userName;

    @Size(min = 3, max = 60, message = "Size password of user between 3 and 60!")
    @NotNull(message = "Password cannot by null!")
    @NotEmpty(message = "Password cannot by empty!")
    private String password;

    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(foreignKey = @ForeignKey(name = "fk_user_id"), inverseForeignKey = @ForeignKey(name = "fk_role_id"),
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
