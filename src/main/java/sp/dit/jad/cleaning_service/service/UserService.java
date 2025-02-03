package sp.dit.jad.cleaning_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.dit.jad.cleaning_service.model.*;
import sp.dit.jad.cleaning_service.repository.*;
import sp.dit.jad.cleaning_service.dto.UserRegistrationDTO;

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
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}