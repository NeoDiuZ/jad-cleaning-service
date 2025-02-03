package sp.dit.jad.cleaning_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sp.dit.jad.cleaning_service.model.Address;
import sp.dit.jad.cleaning_service.model.User;
import sp.dit.jad.cleaning_service.repository.AddressRepository;
import java.util.List;

@org.springframework.stereotype.Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUser(User user) {
        return addressRepository.findByUserOrderByIsPrimaryDesc(user);
    }

    public Address getPrimaryAddress(User user) {
        return addressRepository.findByUserAndIsPrimaryTrue(user);
    }

    @Transactional
    public Address addAddress(Address address, User user) {
        // If this is marked as primary, unset primary flag for other addresses
        if (address.isPrimary()) {
            Address currentPrimary = getPrimaryAddress(user);
            if (currentPrimary != null) {
                currentPrimary.setPrimary(false);
                addressRepository.save(currentPrimary);
            }
        }
        
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long addressId, User user) {
        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));
            
        // Verify ownership
        if (!address.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to delete this address");
        }
        
        // If this was primary, make another address primary
        if (address.isPrimary()) {
            List<Address> otherAddresses = addressRepository.findByUserOrderByIsPrimaryDesc(user);
            if (otherAddresses.size() > 1) {
                Address newPrimary = otherAddresses.stream()
                    .filter(a -> !a.getAddressId().equals(addressId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No other address found"));
                newPrimary.setPrimary(true);
                addressRepository.save(newPrimary);
            }
        }
        
        addressRepository.delete(address);
    }

    @Transactional
    public Address updateAddress(Long addressId, Address updatedAddress, User user) {
        Address existingAddress = addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));
            
        // Verify ownership
        if (!existingAddress.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to update this address");
        }
        
        // If setting this as primary, unset others
        if (updatedAddress.isPrimary() && !existingAddress.isPrimary()) {
            Address currentPrimary = getPrimaryAddress(user);
            if (currentPrimary != null) {
                currentPrimary.setPrimary(false);
                addressRepository.save(currentPrimary);
            }
        }
        
        // Update fields
        existingAddress.setAddressLine1(updatedAddress.getAddressLine1());
        existingAddress.setAddressLine2(updatedAddress.getAddressLine2());
        existingAddress.setFloorNumber(updatedAddress.getFloorNumber());
        existingAddress.setApartmentNumber(updatedAddress.getApartmentNumber());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setPrimary(updatedAddress.isPrimary());
        
        return addressRepository.save(existingAddress);
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public void validateAddressOwnership(Long addressId, User user) {
        Address address = getAddressById(addressId);
        if (!address.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to access this address");
        }
    }
}