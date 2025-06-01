package jets.spring_security.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserDTO {

    private Long id;

    @NotNull(message = "name must not be null")
    @Size(min = 4, max = 20, message = "name must be between 4 and 20 characters")
    private String name;

    private List<String> authorities;

    public UserDTO() {}

    public UserDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
