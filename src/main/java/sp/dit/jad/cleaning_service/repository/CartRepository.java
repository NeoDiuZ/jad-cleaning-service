// Path: src/main/java/sp/dit/jad/cleaning_service/repository/CartRepository.java
package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.dit.jad.cleaning_service.model.CartItem;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.model.Service;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndService(User user, Service service);
    void deleteByUser(User user);
}