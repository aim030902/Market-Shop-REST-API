package uz.aim.marketshop.dtos.category;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private long id;
    private String name;
    private String[] images;
    private Timestamp createdAt;
    private long createdBy;
}
