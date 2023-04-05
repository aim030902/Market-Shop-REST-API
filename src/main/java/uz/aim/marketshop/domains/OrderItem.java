package uz.aim.marketshop.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem extends Auditable {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer count;

    @ManyToOne
    private Order order;

    @Builder(builderMethodName = "childBuilder")
    public OrderItem(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt,
                     Long updatedBy, boolean isDeleted, Long productId, Integer count, Order order) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.productId = productId;
        this.count = count;
        this.order = order;
    }
}
