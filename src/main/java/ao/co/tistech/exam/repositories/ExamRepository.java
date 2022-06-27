package ao.co.tistech.exam.repositories;

import ao.co.tistech.exam.models.ExamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<ExamModel, Long> {

    boolean existsByName(String name);
}
