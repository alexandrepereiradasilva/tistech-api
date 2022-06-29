package ao.co.tistech.exam.services;

import ao.co.tistech.exam.dtos.ExamDto;
import ao.co.tistech.exam.models.ExamModel;
import ao.co.tistech.exam.repositories.ExamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {
    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);
    final ExamRepository examRepository;
    final ObjectMapper objectMapper;

    @Transactional
    public ExamDto save(ExamDto examDto) throws Exception {
        if (Objects.isNull(examDto.getId()) && examRepository.existsByName(examDto.getName())) {
            logger.info("Conflict: Already exists a record with this name!");
            throw new Exception("Conflict: Already exists a record with this name!");
        }

        return objectMapper.convertValue(examRepository.save(objectMapper.convertValue(examDto, ExamModel.class)), ExamDto.class);
    }

    public Optional<ExamDto> findById(Long id) throws Exception {
        Optional<ExamModel> examModelOptional = examRepository.findById(id);

        if (examModelOptional.isEmpty()) {
            logger.info("Exam not found!");
            throw new Exception("Exam not found!");
        }

        return Optional.of(objectMapper.convertValue(examModelOptional.get(), ExamDto.class));
    }

    @Transactional
    public void delete(Long id) throws Exception {
        Optional<ExamDto> examDtoOptional = findById(id);

        if (examDtoOptional.isPresent()) {
            ExamDto examDto = examDtoOptional.get();
            examRepository.delete(objectMapper.convertValue(examDto, ExamModel.class));
        }
    }
}
