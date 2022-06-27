package ao.co.tistech.exam.services;

import ao.co.tistech.exam.dtos.CandidateDto;
import ao.co.tistech.exam.models.CandidateModel;
import ao.co.tistech.exam.repositories.CandidateRepository;
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
public class CandidateService {
    final CandidateRepository candidateRepository;
    final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(CandidateService.class);

    @Transactional
    public CandidateDto save(CandidateDto candidateDto) throws Exception {
        if (Objects.isNull(candidateDto.getId()) && candidateRepository.existsByName(candidateDto.getName())) {
            logger.info("Conflict: Already exists a record with this name!");
            throw new Exception("Conflict: Already exists a record with this name!");
        }

        CandidateModel candidateModel = candidateRepository.save(objectMapper.convertValue(candidateDto, CandidateModel.class));

        return objectMapper.convertValue(candidateModel, CandidateDto.class);
    }

    public Optional<CandidateDto> findById(Long id) throws Exception {
        Optional<CandidateModel> candidateModelOptional = candidateRepository.findById(id);

        if (candidateModelOptional.isEmpty()) {
            logger.info("Candidate not found!");
            throw new Exception("Candidate not found!");
        }

        return Optional.of(objectMapper.convertValue(candidateModelOptional.get(), CandidateDto.class));
    }

    @Transactional
    public void delete(Long id) throws Exception {
        Optional<CandidateDto> candidateDtoOptional = findById(id);

        if (candidateDtoOptional.isPresent()) {
            CandidateDto candidateDto = candidateDtoOptional.get();
            candidateRepository.delete(objectMapper.convertValue(candidateDto, CandidateModel.class));
        }
    }

    public Optional<CandidateDto> findByName(String name) throws Exception {
        Optional<CandidateModel> candidateModelOptional = candidateRepository.findByName(name);

        if (candidateModelOptional.isEmpty()) {
            logger.info("Candidate not found!");
            throw new Exception("Candidate not found!");
        }

        return Optional.of(objectMapper.convertValue(candidateModelOptional.get(), CandidateDto.class));
    }
}
