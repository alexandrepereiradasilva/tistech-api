package ao.co.tistech.exam.repositories;

import ao.co.tistech.exam.models.CandidateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateModel, Long> {

    boolean existsByName(String name);

    Optional<CandidateModel> findByName(String name);
}
