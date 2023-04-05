package uz.aim.marketshop.mapper;

import uz.aim.marketshop.domains.OrderItem;
import uz.aim.marketshop.dtos.basket.OrderItemDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 12/11/22 Saturday 09:33
 * telegram-bot-app/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    List<OrderItemDto> toDto(List<OrderItem> orderItems);
}
