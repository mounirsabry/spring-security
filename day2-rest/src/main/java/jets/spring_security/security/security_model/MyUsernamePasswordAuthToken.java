package jets.spring_security.security.security_model;

import jets.spring_security.model.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyUsernamePasswordAuthToken extends AbstractAuthenticationToken {

    private final String username;
    private final String password;
    private boolean isAuth = false;
    private final User user;

    public MyUsernamePasswordAuthToken(String username, String password,
                Collection<? extends GrantedAuthority> authorities, User user) {

        super(authorities);
        this.username = username;
        this.password = password;
        this.user = user;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuth;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        isAuth = authenticated;
    }
}
