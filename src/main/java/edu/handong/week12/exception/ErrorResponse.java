
package edu.handong.week12.exception;

public record ErrorResponse(
        int status,
        String code,
        String message
) {
}
