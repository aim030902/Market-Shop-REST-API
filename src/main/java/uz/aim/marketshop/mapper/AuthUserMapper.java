package uz.aim.marketshop.mapper;

import uz.aim.marketshop.domains.auth.AuthUser;
import uz.aim.marketshop.dtos.UserRegisterDTO;
import uz.aim.marketshop.dtos.auth.AuthUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUser fromRegisterDTO(UserRegisterDTO dto);

    AuthUserDTO toDTO(AuthUser domain);
}
