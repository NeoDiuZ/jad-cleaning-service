package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sp.dit.jad.cleaning_service.model.BookingDetail;
import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    List<BookingDetail> findByBooking_BookingId(Long bookingId);
}