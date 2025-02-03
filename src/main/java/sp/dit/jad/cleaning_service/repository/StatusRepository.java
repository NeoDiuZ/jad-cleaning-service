package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sp.dit.jad.cleaning_service.model.Status;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT s FROM Status s WHERE LOWER(s.statusName) = LOWER(?1)")
    Optional<Status> findByStatusName(String statusName);
}