package sp.dit.jad.cleaning_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.service.BookingService;
import sp.dit.jad.cleaning_service.service.ServiceListingService;
import sp.dit.jad.cleaning_service.service.UserService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ServiceListingService serviceListingService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Get currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        
        // Add booking statistics
        model.addAttribute("activeBookingsCount", bookingService.countActiveBookingsByUser(user));
        model.addAttribute("completedBookingsCount", bookingService.countCompletedBookingsByUser(user));
        model.addAttribute("availableServicesCount", serviceListingService.countAvailableServices());
        
        // Add recent bookings
        model.addAttribute("recentBookings", bookingService.getRecentBookingsByUser(user, 5));
        
        // Add user details
        model.addAttribute("user", user);
        
        return "customer/dashboard";
    }

    @GetMapping("/services")
    public String showServices(Model model) {
        model.addAttribute("services", serviceListingService.getAllAvailableServices());
        return "customer/services";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("user", user);
        return "customer/profile";
    }
}