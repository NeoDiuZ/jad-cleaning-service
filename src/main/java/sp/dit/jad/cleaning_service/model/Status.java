package sp.dit.jad.cleaning_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    
    @Column(unique = true, nullable = false)
    private String statusName;
}