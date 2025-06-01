package jets.spring_security.security.security_model;

import jets.spring_security.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {

    private final User user;
    private final Collection<GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this.user = user;

        authorities = user.getAuthorities().stream()
                .map(authority -> (GrantedAuthority) () -> authority)
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }
}
