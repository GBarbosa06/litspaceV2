package barbosa.guilherme.LitSpacev2.shared.infrastructure.web.dto;

import java.time.Instant;
import java.util.Map;

public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    public static StandardError of(Integer status, String error, String message, String path) {
        return new StandardError(Instant.now(), status, error, message, path, null);
    }

    public static StandardError withFields(Integer status, String error, String message, String path, Map<String, String> fieldErrors) {
        return new StandardError(Instant.now(), status, error, message, path, fieldErrors);
    }
}