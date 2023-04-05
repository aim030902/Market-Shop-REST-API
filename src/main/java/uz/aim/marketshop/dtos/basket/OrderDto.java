package uz.aim.marketshop.dtos.basket;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private Long userId;
    private double totalPrice;
    private String location;
    private boolean delivered;
}
