package uz.aim.marketshop.dtos.product;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDto {

    @NotBlank(message = "Name cannot be blank!")
    @NotNull(message = "Name can not be null!")
    private String name;

    @NotBlank(message = "Description cannot be blank!")
    @NotNull(message = "Description can not be null!")
    private String description;

    @NotNull(message = "Price can not be null!")
    private double price;

    @NotBlank(message = "Company name can not be blank!")
    @NotNull(message = "Company name can not be null!")
    private String companyName;

    @NotNull(message = "Expiry cannot be null!")
    private Timestamp expiry;

    @NotNull(message = "Category id can not be null!")
    private Long categoryId;
}
