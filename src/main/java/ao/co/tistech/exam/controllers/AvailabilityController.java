package ao.co.tistech.exam.controllers;

import ao.co.tistech.exam.dtos.AvailabilityDto;
import ao.co.tistech.exam.services.AvailabilityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tistech/api/v1/availabilities")
@RequiredArgsConstructor
public class AvailabilityController extends GenericController {
    final AvailabilityService availabilityService;
    final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AvailabilityDto availabilityDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(availabilityService.save(availabilityDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "availabilityIdCache")
    public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
        try {
            Optional<AvailabilityDto> availabilityDtoOptional = availabilityService.findById(id);

            return ResponseEntity.status(HttpStatus.OK).body(availabilityDtoOptional.isPresent() ? availabilityDtoOptional.get() : Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        try {
            availabilityService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).body("Availability deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody @Valid AvailabilityDto availabilityDto) {
        try {
            Optional<AvailabilityDto> availabilityDtoOptional = availabilityService.findById(id);

            if (availabilityDtoOptional.isPresent()) {
                availabilityDtoOptional.get().setName(availabilityDto.getName());

                return ResponseEntity.status(HttpStatus.OK).body(availabilityService.save(availabilityDtoOptional.get()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
