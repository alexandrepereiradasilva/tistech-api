package ao.co.tistech.exam.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomDto {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    private List<AvailabilityDto> availabilities;
}
