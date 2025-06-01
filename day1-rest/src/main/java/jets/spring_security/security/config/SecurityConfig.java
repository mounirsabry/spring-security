package jets.spring_security.security.config;

import jets.spring_security.repo.UserRepo;
import jets.spring_security.security.details_service.JpaUserDetailsService;
import jets.spring_security.security.filter.MyUsernamePasswordHeadersFilter;
import jets.spring_security.security.passwordChecker.MyPasswordEncoder;
import jets.spring_security.security.provider.MyUsernamePasswordProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                AuthenticationManager authManager,
                UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {

        // This will re-add the default basic authentication.
        // http.httpBasic(Customizer.withDefaults());

        // Re-add the login and logout default pages.
        // http.formLogin(Customizer.withDefaults());

        // Configure authorization rules
        http.authorizeHttpRequests(authorizeHttp -> {
            authorizeHttp.requestMatchers("/hello").permitAll();
            authorizeHttp.anyRequest().authenticated();
        });
        
        // My custom basic auth filter.
        http.addFilterBefore(new MyUsernamePasswordHeadersFilter(authManager), BasicAuthenticationFilter.class);

        // Registering the provider, my provider comes before the rest of the spring providers.
        // http.authenticationProvider(new MyUsernamePasswordProvider(userDetailsService, passwordEncoder));

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            MyUsernamePasswordProvider customProvider) throws Exception {

        return new ProviderManager(Collections.singletonList(
                customProvider
        ));
    }

    @Bean
    MyUsernamePasswordProvider myUsernamePasswordProvider(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new MyUsernamePasswordProvider(userDetailsService, passwordEncoder);
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
