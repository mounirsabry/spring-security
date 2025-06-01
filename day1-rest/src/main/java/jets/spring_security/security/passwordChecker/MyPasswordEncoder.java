package jets.spring_security.security.passwordChecker;

import jets.spring_security.utils.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
    The raw password is the password read from the request.
    It is encoded using the encode function.
    The encoded password is the password stored in the database.

    So if the password in the database is stored in base64, then the encode function should
    leave the password as it is if it is already sent as base64.

    If the password is stored using a hash function, then hash the raw password before comparing.
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return PasswordHasher.hash(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
