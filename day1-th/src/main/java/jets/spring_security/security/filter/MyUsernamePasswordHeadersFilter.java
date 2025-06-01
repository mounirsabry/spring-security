package jets.spring_security.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jets.spring_security.utils.DataValidator;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyUsernamePasswordHeadersFilter extends OncePerRequestFilter  {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String userName = request.getHeader("username");
        final String password = request.getHeader("password");

        if (userName == null || password == null) {
            throw new IllegalArgumentException("username and password must not be null");
        }

        DataValidator.validateUsernameString(userName);
        DataValidator.validatePasswordString(password);

        filterChain.doFilter(request, response);
    }
}
