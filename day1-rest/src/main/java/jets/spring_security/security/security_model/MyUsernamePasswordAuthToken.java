package jets.spring_security.security.security_model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyUsernamePasswordAuthToken extends AbstractAuthenticationToken {

    private final String username;
    private final String password;
    private boolean isAuth = false;

    public MyUsernamePasswordAuthToken(String username, String password,
                Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
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
