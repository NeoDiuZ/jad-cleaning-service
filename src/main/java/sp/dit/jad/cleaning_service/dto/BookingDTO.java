package sp.dit.jad.cleaning_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingDTO {
    private Long addressId;
    private LocalDate bookingDate;
    private LocalTime timeSlot;
    private List<Long> services;
    private String specialInstructions;

    // Getters and Setters
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    
    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    
    public LocalTime getTimeSlot() { return timeSlot; }
    public void setTimeSlot(LocalTime timeSlot) { this.timeSlot = timeSlot; }
    
    public List<Long> getServices() { return services; }
    public void setServices(List<Long> services) { this.services = services; }
    
    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { 
        this.specialInstructions = specialInstructions; 
    }
}