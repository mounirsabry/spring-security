package jets.spring_security.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jets.spring_security.security.security_model.MyUsernamePasswordAuthToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyUsernamePasswordHeadersFilter extends OncePerRequestFilter {

    private final AuthenticationManager authManager;

    public MyUsernamePasswordHeadersFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String usernameHeader = request.getHeader("x-username");
        String passwordHeader = request.getHeader("x-password");
        if (usernameHeader == null || usernameHeader.isBlank()
        ||  passwordHeader == null || passwordHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Create the first authentication object which is a bag of credentials to authenticate in the manager/provider.
            Authentication auth = new MyUsernamePasswordAuthToken(usernameHeader, passwordHeader, null);

            Authentication authenticated = authManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(ex.getMessage());
        }
    }
}
