package uz.aim.marketshop.domains;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Builder.Default
    protected Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(nullable = false)
    protected Long createdBy;

    protected Timestamp updatedAt;
    protected Long updatedBy;

    @Builder.Default
    protected boolean isDeleted = false;
}