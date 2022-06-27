package ao.co.tistech.exam.controllers;

import ao.co.tistech.exam.dtos.ExamDto;
import ao.co.tistech.exam.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tistech/api/v1/exams")
@RequiredArgsConstructor
public class ExamController extends GenericController {
    final ExamService examService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ExamDto examDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(examService.save(examDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "examIdCache")
    public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
        try {
            Optional<ExamDto> examDtoOptional = examService.findById(id);

            return ResponseEntity.status(HttpStatus.OK).body(examDtoOptional.isPresent() ? examDtoOptional.get() : Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        try {
            examService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).body("Exam deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody @Valid ExamDto examDto) {
        try {
            Optional<ExamDto> examDtoOptional = examService.findById(id);

            if (examDtoOptional.isPresent()) {
                examDtoOptional.get().setName(examDto.getName());

                return ResponseEntity.status(HttpStatus.OK).body(examService.save(examDtoOptional.get()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
