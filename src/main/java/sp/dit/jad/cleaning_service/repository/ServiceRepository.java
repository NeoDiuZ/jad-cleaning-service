package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sp.dit.jad.cleaning_service.model.Service;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByIsActiveTrue();
    
    @Query("SELECT s FROM Service s WHERE s.isActive = true AND s.category.id = ?1")
    List<Service> findActiveByCategoryId(Long categoryId);
    
    @Query("SELECT COUNT(s) FROM Service s WHERE s.isActive = true")
    long countActiveServices();
}