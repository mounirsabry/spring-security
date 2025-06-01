package jets.spring_security.security.config;

import jets.spring_security.repo.UserRepo;
import jets.spring_security.security.details_service.JpaUserDetailsService;
import jets.spring_security.security.passwordChecker.MyPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // This will re-add the default basic authentication.
        // http.httpBasic(Customizer.withDefaults());

        // Re-add the login and logout default pages.
        // http.formLogin(Customizer.withDefaults());

        // Enable form login with a custom login page.
        http.formLogin(form -> form
            .loginPage("/thLoginForm")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/thGreeting", false)
            .permitAll()
        );

        // Configure logout
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/thLoginForm?logout")
            .permitAll()
        );

        // Configure authorization rules
        http.authorizeHttpRequests(authorizeHttp -> {
                authorizeHttp.requestMatchers("/", "/index").permitAll();
                authorizeHttp.requestMatchers("/thLoginForm").permitAll();
                authorizeHttp.requestMatchers("/css/**", "/js/**", "/images/**").permitAll();
                authorizeHttp.anyRequest().authenticated();
            }
        );

        // Register the filter(s).
        // http.addFilterAt(new LoggingFilter(), AuthorizationFilter.class);

        // This filter was to test sending the username and password in the headers.
        // http.addFilterAt(new MyUsernamePasswordHeadersFilter(), AuthorizationFilter.class);

        return http.build();
    }

    @Bean
    UserDetailsService jpaUserDetailsService(UserRepo userRepo) {
        return new JpaUserDetailsService(userRepo);
    }

    @Bean
    PasswordEncoder myPasswordEncoder() {
        return new MyPasswordEncoder();
    }
}
