
package jets.spring_security.thymeleaf;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeLeafController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/thLoginForm")
    public String login() {
        return "login-form";
    }

    @GetMapping("/thGreeting")
    public String greeting(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("message", String.format("""
            Hello from ThymeLeaf
            Name: %s
            Principal: %s
            Authorities: %s
        """, auth.getName(), auth.getPrincipal(), auth.getAuthorities()));
        return "greeting";
    }
}