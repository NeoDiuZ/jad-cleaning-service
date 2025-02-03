package sp.dit.jad.cleaning_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sp.dit.jad.cleaning_service.service.BookingService;
import sp.dit.jad.cleaning_service.service.ServiceListingService;
import sp.dit.jad.cleaning_service.service.UserService;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.dto.UserRegistrationDTO;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final String ADMIN_REGISTRATION_CODE = "ADMIN123"; // In production, this should be in a secure configuration

    @GetMapping("/register")
    public String showAdminRegistration(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "admin/register";
    }
    
    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute("user") UserRegistrationDTO registrationDTO,
                              @RequestParam String registrationCode,
                              Model model) {
        try {
            if (!ADMIN_REGISTRATION_CODE.equals(registrationCode)) {
                model.addAttribute("error", "Invalid admin registration code");
                return "admin/register";
            }
            
            if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
                model.addAttribute("error", "Passwords do not match");
                return "admin/register";
            }
            
            userService.registerNewAdmin(registrationDTO);
            model.addAttribute("success", "Admin registration successful! Please login.");
            return "admin/register";
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/register";
        }
    }

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ServiceListingService serviceListingService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        User admin = userService.findByEmail(authentication.getName());
        
        // Add statistics to the model
        model.addAttribute("totalBookings", bookingService.getTotalBookingsCount());
        model.addAttribute("activeBookings", bookingService.getActiveBookingsCount());
        model.addAttribute("totalServices", serviceListingService.countAvailableServices());
        model.addAttribute("totalCustomers", userService.getCustomerCount());
        
        // Add recent bookings
        model.addAttribute("recentBookings", bookingService.getRecentBookings(10));
        
        // Add user information
        model.addAttribute("admin", admin);
        
        return "admin/dashboard";
    }

    @GetMapping("/services")
    public String showServices(Model model) {
        model.addAttribute("services", serviceListingService.getAllAvailableServices());
        return "admin/services";
    }

    @GetMapping("/customers")
    public String showCustomers(Model model) {
        model.addAttribute("customers", userService.getAllCustomers());
        return "admin/customers";
    }

    @GetMapping("/bookings")
    public String showBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/bookings";
    }
}