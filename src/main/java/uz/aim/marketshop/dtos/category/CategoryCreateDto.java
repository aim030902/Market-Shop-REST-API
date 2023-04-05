package uz.aim.marketshop.dtos.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {

    @NotBlank(message = "Name can not be null!")
    @NotNull(message = "Name can not be null!")
    private String name;
}
