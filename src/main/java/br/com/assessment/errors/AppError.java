package br.com.assessment.errors;

import org.springframework.http.HttpStatus;

public class AppError extends Exception {

    private final HttpStatus statusCode;

    public AppError(String message, HttpStatus statusCode) {
        super(message);

        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }
}
