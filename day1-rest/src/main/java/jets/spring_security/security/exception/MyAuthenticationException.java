package jets.spring_security.security.exception;

import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {

    public MyAuthenticationException(String msg) {
        super(msg);
    }
}
