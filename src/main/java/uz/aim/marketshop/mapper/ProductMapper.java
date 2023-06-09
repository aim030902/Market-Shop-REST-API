package uz.aim.marketshop.mapper;

import uz.aim.marketshop.domains.Product;
import uz.aim.marketshop.dtos.product.ProductDto;
import uz.aim.marketshop.dtos.product.ProductUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 12:26
 * telegram-bot-app/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    List<ProductDto> toDto(List<Product> productList);

    void update(ProductUpdateDto dto, @MappingTarget Product product);
}
