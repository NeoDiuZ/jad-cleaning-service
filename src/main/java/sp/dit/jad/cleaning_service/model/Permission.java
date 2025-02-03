package sp.dit.jad.cleaning_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "permissions")
class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;
    
    @Column(unique = true, nullable = false)
    private String permissionName;
}
