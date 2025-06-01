package jets.spring_security.service.impl;

import jets.spring_security.exception.NotFoundException;
import jets.spring_security.model.dto.UserDTO;
import jets.spring_security.model.entity.User;
import jets.spring_security.model.mapper.UserMapper;
import jets.spring_security.repo.UserRepo;
import jets.spring_security.service.UserService;
import jets.spring_security.utils.DataValidator;
import jets.spring_security.utils.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserDTO> getAll() {
        return userRepo.findAll().stream()
                .map(UserMapper::entityToDTO)
                .toList();
    }

    public UserDTO getById(long id) {
        User entity = userRepo.findById(id);
        if (entity == null) {
            return null;
        }
        return UserMapper.entityToDTO(entity);
    }

    @Override
    public void update(UserDTO dto) {
        DataValidator.validateId(dto.getId());
        long id = dto.getId();

        User entity = userRepo.findById(id);
        if (entity == null) {
            throw new NotFoundException("No user was found with id: " + id);
        }

        UserMapper.updateFromDTO(entity, dto);
        userRepo.save(entity);
    }

    @Override
    public void deleteById(long id) {
        DataValidator.validateId(id);

        User entity = userRepo.findById(id);
        if (entity == null) {
            throw new NotFoundException("No user was found with id: " + id);
        }

        userRepo.deleteById(id);
    }

    @Override
    public UserDTO register(UserDTO dto, String password) {
        DataValidator.validatePasswordString(password);

        User entity = UserMapper.dtoToEntity(dto);
        entity.setPassword(PasswordHasher.hash(password));

        User registeredUser = userRepo.save(entity);
        dto.setId(registeredUser.getId());
        return dto;
    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) {
        DataValidator.validateId(id);
        DataValidator.validatePasswordString(oldPassword);
        DataValidator.validatePasswordString(newPassword);

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("Old and new passwords cannot be the same");
        }

        User entity = userRepo.findById(id);
        if (entity == null) {
            throw new NotFoundException("No user was found with id: " + id);
        }

        if (!PasswordHasher.hash(oldPassword).equals(entity.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        String hashedPassword = PasswordHasher.hash(newPassword);
        entity.setPassword(hashedPassword);
        userRepo.save(entity);
    }
}
