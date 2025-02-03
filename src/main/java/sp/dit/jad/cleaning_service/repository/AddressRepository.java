package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.dit.jad.cleaning_service.model.Address;
import sp.dit.jad.cleaning_service.model.User;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserOrderByIsPrimaryDesc(User user);
    Address findByUserAndIsPrimaryTrue(User user);
}