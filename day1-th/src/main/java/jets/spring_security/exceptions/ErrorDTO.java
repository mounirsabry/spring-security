package jets.spring_security.exceptions;

import java.time.LocalDate;

public record ErrorDTO(
        String errorCode,
        String message,
        String path,
        LocalDate timestamp) {
}
