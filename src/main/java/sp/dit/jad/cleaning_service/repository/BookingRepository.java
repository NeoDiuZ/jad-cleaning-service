package sp.dit.jad.cleaning_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.PageRequest;
import sp.dit.jad.cleaning_service.model.Booking;
import sp.dit.jad.cleaning_service.model.User;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerOrderByBookingDateDesc(User customer);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = ?1 AND b.status.statusName IN ('Scheduled', 'In Progress')")
    List<Booking> findActiveBookingsByCustomer(User customer);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.customer = ?1 AND b.status.statusName IN ('Scheduled', 'In Progress')")
    long countActiveBookingsByCustomer(User customer);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.customer = ?1 AND b.status.statusName = 'Completed'")
    long countCompletedBookingsByCustomer(User customer);

    List<Booking> findAllByOrderByBookingDateDesc();
    List<Booking> findByStatus_StatusName(String statusName);
    List<Booking> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);
    long countByStatus_StatusNameIn(List<String> statusNames);
    List<Booking> findByCustomerOrderByBookingDateDesc(User customer, PageRequest pageRequest);
    
    @Query("SELECT b FROM Booking b WHERE b.customer = ?1 ORDER BY b.bookingDate DESC")
    List<Booking> findRecentBookingsByCustomer(User user);


}