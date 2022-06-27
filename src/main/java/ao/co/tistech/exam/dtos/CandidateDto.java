package ao.co.tistech.exam.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateDto implements Serializable {
    private Long id;

    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Email
    private String email;

}
