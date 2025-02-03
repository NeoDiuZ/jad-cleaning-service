package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sp.dit.jad.cleaning_service.model.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE LOWER(r.roleName) = LOWER(?1)")
    Optional<Role> findByRoleName(String roleName);
}	