package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.dit.jad.cleaning_service.model.*;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    long countByRole(Role role);
    long countByRole_RoleName(String roleName);
    List<User> findByRole_RoleName(String roleName);
}