package jets.spring_security.repo;

import jets.spring_security.model.entity.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepo extends Repository<User, Long> {

    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User save(User user);
    void deleteById(Long id);
}
