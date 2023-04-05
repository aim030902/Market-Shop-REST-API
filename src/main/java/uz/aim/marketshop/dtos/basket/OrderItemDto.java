package uz.aim.marketshop.dtos.basket;

import uz.aim.marketshop.dtos.product.ProductDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private Integer count;
}
