package sp.dit.jad.cleaning_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import sp.dit.jad.cleaning_service.repository.ServiceRepository;
import sp.dit.jad.cleaning_service.model.Service;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceListingService {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    public List<Service> getAllAvailableServices() {
        return serviceRepository.findByIsActiveTrue();
    }
    
    public List<Service> getServicesByCategory(Long categoryId) {
        return serviceRepository.findActiveByCategoryId(categoryId);
    }
    
    public long countAvailableServices() {
        return serviceRepository.countActiveServices();
    }
    
    public Service getServiceById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }
    
    public List<Service> getServicesByIds(List<Long> ids) {
        return serviceRepository.findAllById(ids);
    }
}