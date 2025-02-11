package sp.dit.jad.cleaning_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sp.dit.jad.cleaning_service.service.UserService;
import sp.dit.jad.cleaning_service.dto.UserRegistrationDTO;
import sp.dit.jad.cleaning_service.dto.AdminRegistrationDTO;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private static final String ADMIN_REGISTRATION_CODE = "ADMIN123"; // In production, this should be in configuration
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLoginPage(Model model, 
                              @RequestParam(required = false) String error,
                              @RequestParam(required = false) String logout,
                              @RequestParam(required = false) String registered) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password");
        }
        
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        
        if (registered != null) {
            model.addAttribute("message", "Registration successful! Please login");
        }
        
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDTO registrationDTO, 
                             Model model) {
        try {
            if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
                model.addAttribute("error", "Passwords do not match");
                return "auth/register";
            }
            
            userService.registerNewUser(registrationDTO);
            return "redirect:/auth/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // Updated admin registration methods
    @GetMapping("/register-admin")
    public String showAdminRegistrationPage(Model model) {
        model.addAttribute("user", new AdminRegistrationDTO());
        return "auth/register-admin"; // Changed to auth directory
    }
    
    @PostMapping("/register-admin")
    public String registerAdmin(@ModelAttribute("user") AdminRegistrationDTO registrationDTO, 
                              Model model) {
        try {
            // Validate admin registration code
            if (!ADMIN_REGISTRATION_CODE.equals(registrationDTO.getAdminCode())) {
                model.addAttribute("error", "Invalid admin registration code");
                return "auth/register-admin";
            }
            
            // Validate passwords match
            if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
                model.addAttribute("error", "Passwords do not match");
                return "auth/register-admin";
            }
            
            userService.registerNewAdmin(registrationDTO);
            return "redirect:/auth/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register-admin";
        }
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "auth/access-denied";
    }

    @GetMapping("/dashboard")
    public String handleDashboardRedirect(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            return "redirect:/customer/dashboard";
        }
        return "redirect:/dashboard";
    }
}