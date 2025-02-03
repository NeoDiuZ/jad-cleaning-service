package sp.dit.jad.cleaning_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.service.BookingService;
import sp.dit.jad.cleaning_service.service.ServiceListingService;
import sp.dit.jad.cleaning_service.service.AddressService;
import sp.dit.jad.cleaning_service.dto.BookingDTO;

@Controller
@RequestMapping("/customer/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ServiceListingService serviceListingService;
    
    @Autowired
    private AddressService addressService;

    @GetMapping
    public String viewBookings(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("bookings", bookingService.getBookingsByUser(user));
        return "customer/bookings";
    }

    @GetMapping("/{id}")
    public String viewBookingDetails(@PathVariable Long id, Model model, 
                                   @AuthenticationPrincipal User user) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "customer/booking-details";
    }

    @PostMapping("/new")
    public String showBookingForm(@RequestParam("services") java.util.List<Long> services, 
                                Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("selectedServices", serviceListingService.getServicesByIds(services));
        model.addAttribute("addresses", addressService.getAddressesByUser(user));
        return "customer/booking-form";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute BookingDTO bookingDTO, 
                              @AuthenticationPrincipal User user) {
        try {
            var booking = bookingService.createBooking(bookingDTO, user);
            return "redirect:/customer/bookings/" + booking.getBookingId();
        } catch (Exception e) {
            return "redirect:/customer/bookings/new?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, @AuthenticationPrincipal User user) {
        bookingService.cancelBooking(id, user);
        return "redirect:/customer/bookings";
    }
}