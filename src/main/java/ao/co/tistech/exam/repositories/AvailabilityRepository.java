package ao.co.tistech.exam.repositories;

import ao.co.tistech.exam.models.AvailabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityModel, Long> {

    boolean existsByName(String name);
}
