package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sp.dit.jad.cleaning_service.model.Booking;
import sp.dit.jad.cleaning_service.model.User;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerOrderByBookingDateDesc(User customer);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = ?1 AND b.status.statusName IN ('Scheduled', 'In Progress')")
    List<Booking> findActiveBookingsByCustomer(User customer);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.customer = ?1 AND b.status.statusName IN ('Scheduled', 'In Progress')")
    long countActiveBookingsByCustomer(User customer);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.customer = ?1 AND b.status.statusName = 'Completed'")
    long countCompletedBookingsByCustomer(User customer);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = ?1 ORDER BY b.bookingDate DESC, b.timeSlot DESC")
    List<Booking> findRecentBookingsByCustomer(User customer);
}