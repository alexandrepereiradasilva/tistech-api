package ao.co.tistech.exam.repositories;

import ao.co.tistech.exam.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {

    boolean existsByName(String name);
}
