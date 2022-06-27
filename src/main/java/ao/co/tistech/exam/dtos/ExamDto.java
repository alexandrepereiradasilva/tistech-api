package ao.co.tistech.exam.dtos;

import ao.co.tistech.exam.models.AvailabilityModel;
import ao.co.tistech.exam.models.CandidateModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamDto implements Serializable {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;
    private AvailabilityDto availability;
    private CandidateDto candidate;
}
