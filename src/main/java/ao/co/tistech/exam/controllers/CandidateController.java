package ao.co.tistech.exam.controllers;

import ao.co.tistech.exam.dtos.CandidateDto;
import ao.co.tistech.exam.services.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tistech/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController extends GenericController {
    final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid CandidateDto candidateDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.save(candidateDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "candidateIdCache")
    public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
        try {
            Optional<CandidateDto> candidateDtoOptional = candidateService.findById(id);

            return ResponseEntity.status(HttpStatus.OK).body(candidateDtoOptional.isPresent() ? candidateDtoOptional.get() : Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        try {
            candidateService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).body("Candidate deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody @Valid CandidateDto candidateDto) {
        try {
            Optional<CandidateDto> candidateDtoOptional = candidateService.findById(id);

            if (candidateDtoOptional.isPresent()) {
                candidateDtoOptional.get().setName(candidateDto.getName());
                candidateDtoOptional.get().setEmail(candidateDto.getEmail());

                return ResponseEntity.status(HttpStatus.OK).body(candidateService.save(candidateDtoOptional.get()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    @Cacheable(value = "candidateNameCache")
    public ResponseEntity<Object> getByName(@PathVariable(value = "name") String name) {
        try {
            Optional<CandidateDto> candidateDtoOptional = candidateService.findByName(name);

            return ResponseEntity.status(HttpStatus.OK).body(candidateDtoOptional.isPresent() ? candidateDtoOptional.get() : Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
