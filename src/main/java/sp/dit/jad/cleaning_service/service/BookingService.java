package sp.dit.jad.cleaning_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import sp.dit.jad.cleaning_service.model.*;
import sp.dit.jad.cleaning_service.repository.*;
import sp.dit.jad.cleaning_service.dto.BookingDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Arrays;


@Service
public class BookingService {
    
    private static final BigDecimal GST_RATE = new BigDecimal("0.08"); // 8% GST
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private StatusRepository statusRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByCustomerOrderByBookingDateDesc(user);
    }
    
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }
    
    public long getTotalBookingsCount() {
        return bookingRepository.count();
    }
    
    public long getActiveBookingsCount() {
        return bookingRepository.countByStatus_StatusNameIn(Arrays.asList("Scheduled", "In Progress"));
    }
    
    public List<Booking> getRecentBookings(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "bookingDate"));
        return bookingRepository.findAll(pageRequest).getContent();
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "bookingDate"));
    }
    
    public long countActiveBookingsByUser(User user) {
        return bookingRepository.countActiveBookingsByCustomer(user);
    }
    
    public long countCompletedBookingsByUser(User user) {
        return bookingRepository.countCompletedBookingsByCustomer(user);
    }
    
    public List<Booking> getRecentBookingsByUser(User user, int limit) {
        List<Booking> bookings = bookingRepository.findRecentBookingsByCustomer(user);
        return bookings.size() > limit ? bookings.subList(0, limit) : bookings;
    }
    
    
    public List<Booking> getAllBookingsByUser(User user) {
        return bookingRepository.findByCustomerOrderByBookingDateDesc(user);
    }
    
    @Transactional
    public Booking createBooking(BookingDTO bookingDTO, User customer) {
        // Get the address
        Address address = addressRepository.findById(bookingDTO.getAddressId())
            .orElseThrow(() -> new RuntimeException("Address not found"));
            
        // Validate address belongs to customer
        if (!address.getUser().getUserId().equals(customer.getUserId())) {
            throw new RuntimeException("Invalid address");
        }
        
        // Get the scheduled status
        Status scheduledStatus = statusRepository.findByStatusName("Scheduled")
            .orElseThrow(() -> new RuntimeException("Status not found"));
        
        // Create new booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setAddress(address);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setTimeSlot(bookingDTO.getTimeSlot());
        booking.setStatus(scheduledStatus);
        booking.setSpecialInstructions(bookingDTO.getSpecialInstructions());
        booking.setPaymentStatus("Pending");
        
        // Calculate amounts
        BigDecimal totalBeforeGst = calculateTotalAmount(bookingDTO.getServices());
        BigDecimal gstAmount = totalBeforeGst.multiply(GST_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = totalBeforeGst.add(gstAmount);
        
        booking.setTotalAmount(totalAmount);
        booking.setGstAmount(gstAmount);
        
        // Save booking
        booking = bookingRepository.save(booking);
        
        // Create booking details
        createBookingDetails(booking, bookingDTO.getServices());
        
        return booking;
    }
    
    private BigDecimal calculateTotalAmount(List<Long> serviceIds) {
        BigDecimal total = BigDecimal.ZERO;
        for (Long serviceId : serviceIds) {
            sp.dit.jad.cleaning_service.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
            total = total.add(service.getBasePrice());
        }
        return total;
    }
    
    private void createBookingDetails(Booking booking, List<Long> serviceIds) {
        for (Long serviceId : serviceIds) {
            sp.dit.jad.cleaning_service.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
                
            BookingDetail detail = new BookingDetail();
            detail.setBooking(booking);
            detail.setService(service);
            detail.setQuantity(1);
            detail.setUnitPrice(service.getBasePrice());
            detail.calculateSubtotal();
            // Save booking detail
        }
    }
    
    @Transactional
    public void cancelBooking(Long bookingId, User customer) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
            
        // Verify booking belongs to customer
        if (!booking.getCustomer().getUserId().equals(customer.getUserId())) {
            throw new RuntimeException("Unauthorized access to booking");
        }
        
        // Get cancelled status
        Status cancelledStatus = statusRepository.findByStatusName("Cancelled")
            .orElseThrow(() -> new RuntimeException("Status not found"));
            
        booking.setStatus(cancelledStatus);
        bookingRepository.save(booking);
    }
}