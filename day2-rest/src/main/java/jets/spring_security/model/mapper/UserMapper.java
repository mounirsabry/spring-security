package jets.spring_security.model.mapper;

import jets.spring_security.model.dto.UserDTO;
import jets.spring_security.model.entity.User;

public class UserMapper {

    public static UserDTO entityToDTO(User entity) {
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getUsername());
        dto.setAuthorities(entity.getAuthorities());

        return dto;
    }

    public static User dtoToEntity(UserDTO dto) {
        User entity = new User();

        entity.setId(dto.getId());
        entity.setUsername(dto.getName());
        entity.setAuthorities(entity.getAuthorities());

        return entity;
    }

    public static void updateFromDTO(User entity, UserDTO dto) {
        entity.setUsername(dto.getName());
    }
}
