package uz.aim.marketshop.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class Order extends Auditable {

    @Column(nullable = false)
    private Long userId;
    private Double totalPrice;
    private String location;

    private boolean delivered;
    private boolean active;

    @Builder(builderMethodName = "childBuilder")
    public Order(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt,
                 Long updatedBy, boolean isDeleted, Long userId,
                 Double totalPrice, String location, boolean delivered, boolean active) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.location = location;
        this.delivered = delivered;
        this.active = active;
    }
}
