package jets.spring_security.service;

import jets.spring_security.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();

    UserDTO getById(long id);

    void update(UserDTO dto);

    void deleteById(long id);

    UserDTO register(UserDTO dto, String password);

    void changePassword(long id, String oldPassword, String newPassword);
}
