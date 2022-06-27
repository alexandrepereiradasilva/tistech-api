package ao.co.tistech.exam.controllers;

import ao.co.tistech.exam.dtos.RoomDto;
import ao.co.tistech.exam.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tistech/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController extends GenericController {
    final RoomService roomService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid RoomDto roomDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(roomDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Cacheable(value = "roomIdCache")
    public ResponseEntity<Object> get(@PathVariable(value = "id") Long id) {
        try {
            Optional<RoomDto> roomDtoOptional = roomService.findById(id);

            return ResponseEntity.status(HttpStatus.OK).body(roomDtoOptional.isPresent() ? roomDtoOptional.get() : Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        try {
            roomService.delete(id);

            return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody @Valid RoomDto roomDto) {
        try {
            Optional<RoomDto> roomDtoOptional = roomService.findById(id);

            if (roomDtoOptional.isPresent()) {
                roomDtoOptional.get().setName(roomDto.getName());

                return ResponseEntity.status(HttpStatus.OK).body(roomService.save(roomDtoOptional.get()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Optional.empty());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
