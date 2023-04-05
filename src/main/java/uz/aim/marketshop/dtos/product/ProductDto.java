package uz.aim.marketshop.dtos.product;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String companyName;
    private Timestamp expiry;
    private String[] images;
    private Long categoryId;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private boolean isDeleted;
}
