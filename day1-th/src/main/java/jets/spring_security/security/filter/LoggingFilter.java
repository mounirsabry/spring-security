package jets.spring_security.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Hello from LoggingFilter");
        System.out.println("Request: " + request);
        System.out.println("Response: " + response);
        System.out.println("FilterChain: " + filterChain);
        filterChain.doFilter(request, response);
    }
}
