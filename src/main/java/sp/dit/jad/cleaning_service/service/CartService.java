// Path: src/main/java/sp/dit/jad/cleaning_service/service/CartService.java
package sp.dit.jad.cleaning_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sp.dit.jad.cleaning_service.model.CartItem;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.repository.CartRepository;
import sp.dit.jad.cleaning_service.repository.ServiceRepository;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;

    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }
    
    @Transactional
    public CartItem addToCart(User user, Long serviceId) {
        var service = serviceRepository.findById(serviceId)
            .orElseThrow(() -> new RuntimeException("Service not found"));
            
        var existingItem = cartRepository.findByUserAndService(user, service);
        
        if (existingItem.isPresent()) {
            var item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            return cartRepository.save(item);
        } else {
            var newItem = new CartItem();
            newItem.setUser(user);
            newItem.setService(service);
            newItem.setQuantity(1);
            return cartRepository.save(newItem);
        }
    }
    
    @Transactional
    public void removeFromCart(User user, Long serviceId) {
        var service = serviceRepository.findById(serviceId)
            .orElseThrow(() -> new RuntimeException("Service not found"));
            
        var existingItem = cartRepository.findByUserAndService(user, service)
            .orElseThrow(() -> new RuntimeException("Item not found in cart"));
            
        cartRepository.delete(existingItem);
    }
    
    @Transactional
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }
}