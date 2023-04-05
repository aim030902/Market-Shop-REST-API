package uz.aim.marketshop.domains;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends Auditable {

    @Column(nullable = false, unique = true)
    private String name;

    @Builder(builderMethodName = "childBuilder")
    public Category(Long id, Timestamp createdAt, Long createdBy, Timestamp updatedAt,
                    Long updatedBy, boolean isDeleted, String name) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, isDeleted);
        this.name = name;
    }
}
