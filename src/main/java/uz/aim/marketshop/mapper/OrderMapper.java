package uz.aim.marketshop.mapper;

import uz.aim.marketshop.domains.Order;
import uz.aim.marketshop.dtos.basket.OrderDto;
import org.mapstruct.Mapper;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 12/11/22 Saturday 09:12
 * telegram-bot-app/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
