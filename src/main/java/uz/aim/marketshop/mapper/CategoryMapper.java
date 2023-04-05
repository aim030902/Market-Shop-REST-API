package uz.aim.marketshop.mapper;

import uz.aim.marketshop.domains.Category;
import uz.aim.marketshop.dtos.category.CategoryUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 12:24
 * telegram-bot-app/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    void update(CategoryUpdateDto dto, @MappingTarget Category category);
}
