package jets.spring_security.security.provider;

import jets.spring_security.security.exception.MyAuthenticationException;
import jets.spring_security.security.security_model.MyUsernamePasswordAuthToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyUsernamePasswordProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public MyUsernamePasswordProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String sentPassword = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String storedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(sentPassword, storedPassword)) {
            throw new MyAuthenticationException("Invalid username or password");
        }

        // Create the second authentication object which represents the authenticated user.
        Authentication successAuth = new MyUsernamePasswordAuthToken(username, null, userDetails.getAuthorities());
        successAuth.setAuthenticated(true);

        return successAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MyUsernamePasswordAuthToken.class.isAssignableFrom(authentication);
    }
}
