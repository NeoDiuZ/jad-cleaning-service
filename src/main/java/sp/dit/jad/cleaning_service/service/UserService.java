package sp.dit.jad.cleaning_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.dit.jad.cleaning_service.model.*;
import sp.dit.jad.cleaning_service.repository.*;
import sp.dit.jad.cleaning_service.dto.UserRegistrationDTO;
import sp.dit.jad.cleaning_service.dto.AdminRegistrationDTO;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private StatusRepository statusRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public long getCustomerCount() {
        return userRepository.countByRole_RoleName("CUSTOMER");
    }
    
    public List<User> getAllCustomers() {
        return userRepository.findByRole_RoleName("CUSTOMER");
    }
    
    @Transactional
    public void registerNewUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDTO.getPassword()));
        
        // Get CUSTOMER role
        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
            .orElseThrow(() -> new RuntimeException("Default role not found"));
            
        // Get ACTIVE status
        Status activeStatus = statusRepository.findByStatusName("Active")
            .orElseThrow(() -> new RuntimeException("Default status not found"));
        
        user.setRole(customerRole);
        user.setStatus(activeStatus);
        
        userRepository.save(user);
    }

    @Transactional
    public void registerNewAdmin(AdminRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User admin = new User();
        admin.setEmail(registrationDTO.getEmail());
        admin.setPasswordHash(passwordEncoder.encode(registrationDTO.getPassword()));
        
        // Get ADMIN role
        Role adminRole = roleRepository.findByRoleName("ADMIN")
            .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
        // Get ACTIVE status
        Status activeStatus = statusRepository.findByStatusName("Active")
            .orElseThrow(() -> new RuntimeException("Default status not found"));
        
        admin.setRole(adminRole);
        admin.setStatus(activeStatus);
        
        userRepository.save(admin);
    }
    
    @Transactional
    public User registerNewAdmin(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User admin = new User();
        admin.setEmail(registrationDTO.getEmail());
        admin.setPasswordHash(passwordEncoder.encode(registrationDTO.getPassword()));
        
        // Get ADMIN role
        Role adminRole = roleRepository.findByRoleName("ADMIN")
            .orElseThrow(() -> new RuntimeException("Admin role not found"));
            
        // Get ACTIVE status
        Status activeStatus = statusRepository.findByStatusName("Active")
            .orElseThrow(() -> new RuntimeException("Default status not found"));
        
        admin.setRole(adminRole);
        admin.setStatus(activeStatus);
        
        return userRepository.save(admin);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}