package org.scopesky.jdktutorial.models;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private String error;
    private String path;
    private LocalDateTime timestamp;
    private String trace;
    public ErrorResponse(int status, String message, String error, String path, LocalDateTime timestamp, String trace) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
        this.trace = trace;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTrace() {
        return trace;
    }
}
