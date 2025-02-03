package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.dit.jad.cleaning_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
