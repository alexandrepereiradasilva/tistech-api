package ao.co.tistech.exam.services;

import ao.co.tistech.exam.dtos.RoomDto;
import ao.co.tistech.exam.models.RoomModel;
import ao.co.tistech.exam.repositories.RoomRepository;
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
public class RoomService {
    final RoomRepository roomRepository;
    final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Transactional
    public RoomDto save(RoomDto roomDto) throws Exception {
        if (Objects.isNull(roomDto.getId()) && roomRepository.existsByName(roomDto.getName())) {
            logger.info("Conflict: Already exists a record with this name!");
            throw new Exception("Conflict: Already exists a record with this name!");
        }

        return objectMapper.convertValue(roomRepository.save(objectMapper.convertValue(roomDto, RoomModel.class)), RoomDto.class);
    }

    public Optional<RoomDto> findById(Long id) throws Exception {
        Optional<RoomModel> roomModelOptional = roomRepository.findById(id);

        if (roomModelOptional.isEmpty()) {
            logger.info("Conflict: Already exists a record with this name!");
            throw new Exception("Room not found!");
        }

        return Optional.of(objectMapper.convertValue(roomModelOptional.get(), RoomDto.class));
    }

    @Transactional
    public void delete(Long id) throws Exception {
        Optional<RoomDto> roomDtoOptional = findById(id);

        if (roomDtoOptional.isPresent()) {
            RoomDto roomDto = roomDtoOptional.get();
            roomRepository.delete(objectMapper.convertValue(roomDto, RoomModel.class));
        }
    }
}
