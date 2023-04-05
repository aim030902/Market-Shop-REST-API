package uz.aim.marketshop.mapper;

import uz.aim.marketshop.domains.auth.AuthRole;
import uz.aim.marketshop.dtos.auth.AuthRoleCreateDTO;
import uz.aim.marketshop.dtos.auth.AuthRoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthRoleMapper {
    AuthRoleDTO toDTO(AuthRole entity);

    List<AuthRoleDTO> toDTO(List<AuthRole> entities);

    AuthRole fromCreateDTO(AuthRoleCreateDTO dto);
}
