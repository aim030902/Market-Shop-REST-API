package uz.aim.marketshop.dtos.basket;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto {

    @NotNull(message = "Location can not be null!")
    @NotBlank(message = "Location can not be blank!")
    private String location;
}
