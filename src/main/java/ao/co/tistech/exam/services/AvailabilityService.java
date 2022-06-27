package ao.co.tistech.exam.services;

import ao.co.tistech.exam.dtos.AvailabilityDto;
import ao.co.tistech.exam.models.AvailabilityModel;
import ao.co.tistech.exam.repositories.AvailabilityRepository;
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
public class AvailabilityService {
    final AvailabilityRepository availabilityRepository;
    final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    @Transactional
    public AvailabilityDto save(AvailabilityDto availabilityDto) throws Exception {
        if (Objects.isNull(availabilityDto.getId())) {
            if (existsByName(availabilityDto.getName())) {
                logger.info("Conflict: Already exists a record with this name!");
                throw new Exception("Conflict: Already exists a record with this name!");
            }
        }

        return objectMapper.convertValue(availabilityRepository.save(objectMapper.convertValue(availabilityDto, AvailabilityModel.class)), AvailabilityDto.class);
    }

    public Optional<AvailabilityDto> findById(Long id) throws Exception {
        Optional<AvailabilityModel> availabilityModelOptional = availabilityRepository.findById(id);

        if (availabilityModelOptional.isEmpty()) {
            logger.info("Availability not found!");
            throw new Exception("Availability not found!");
        }

        return Optional.of(objectMapper.convertValue(availabilityModelOptional.get(), AvailabilityDto.class));
    }

    @Transactional
    public void delete(Long id) throws Exception {
        Optional<AvailabilityDto> availabilityDtoOptional = findById(id);

        if (availabilityDtoOptional.isPresent()) {
            AvailabilityDto availabilityDto = availabilityDtoOptional.get();
            availabilityRepository.delete(objectMapper.convertValue(availabilityDto, AvailabilityModel.class));
        }
    }

    public boolean existsByName(String name) {
        return availabilityRepository.existsByName(name);
    }
}
