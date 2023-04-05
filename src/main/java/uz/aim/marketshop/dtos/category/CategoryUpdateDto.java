package uz.aim.marketshop.dtos.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpdateDto {

    @NotNull(message = "Id can not be null!")
    private Long id;

    @NotBlank(message = "Name can not be blank!")
    @NotNull(message = "Name can not be null!")
    private String name;
}
