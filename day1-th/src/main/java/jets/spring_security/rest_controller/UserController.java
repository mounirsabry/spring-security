package jets.spring_security.rest_controller;

import jets.spring_security.model.dto.UserDTO;
import jets.spring_security.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping(value = "/hello", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> hello(@RequestBody HelloDTO dto) {
        System.out.println(dto);

        if (dto == null) {
            throw new IllegalArgumentException("dto must not be null");
        }
        if (dto.name() == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        if (dto.name().isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }

        return ResponseEntity.ok("Hello " + dto.name() + "!");
    }

    record HelloDTO(String name) {}

    @GetMapping
    ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping
    ResponseEntity<String> update(@RequestBody UserDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto must not be null");
        }
        if (dto.getId() == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        userService.update(dto);
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        userService.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PostMapping("/register")
    ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDTO) {
        if (registerDTO == null) {
            throw new IllegalArgumentException("dto must not be null");
        }

        UserDTO dto = new UserDTO(registerDTO.name());
        UserDTO registeredDTO = userService.register(dto, registerDTO.password());

        return ResponseEntity.ok(registeredDTO);
    }

    record RegisterDTO(String name, String password) {}

    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("dto must not be null");
        }

        userService.changePassword(dto.id(), dto.oldPassword(), dto.newPassword());
        return ResponseEntity.ok("Password changed");
    }

    record ChangePasswordDTO(long id, String oldPassword, String newPassword) {}
}
