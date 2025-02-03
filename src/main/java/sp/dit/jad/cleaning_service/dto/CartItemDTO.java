// Path: src/main/java/sp/dit/jad/cleaning_service/dto/CartItemDTO.java
package sp.dit.jad.cleaning_service.dto;

import java.math.BigDecimal;

public class CartItemDTO {
    private Long serviceId;
    private String serviceName;
    private BigDecimal price;
    private Integer quantity;
    private Integer duration; 

    // Getters and Setters
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}