package com.tilepay.upholdclient.model.error;

public class UpholdError {

    private static final UpholdError BAD_REQUEST = new UpholdError(400, "Bad Request – Validation failed.");
    private static final UpholdError UNAUTHORIZED = new UpholdError(401, "Unauthorized – Bad credentials.");
    private static final UpholdError FORBIDDEN = new UpholdError(403, "Forbidden – Access forbidden.");
    private static final UpholdError NOT_FOUND = new UpholdError(404, "Not Found – Object not found.");
    private static final UpholdError PRECONDITION_FAILED = new UpholdError(412, "Precondition Failed.");
    private static final UpholdError REQUESTED_RANGE_NOT_SATISFIABLE = new UpholdError(416, "Requested Range Not Satisfiable.");
    private static final UpholdError TOO_MANY_REQUESTS = new UpholdError(429, "Too Many Requests – Rate limit exceeded.");
    private static final UpholdError INTERNAL_SERVER_ERROR = new UpholdError(500, "Internal Server Error – Something went wrong in our server.");
    private static final UpholdError SERVICE_UNAVAILABLE = new UpholdError(503, "Unavailable – We’re temporarily offline for maintenance. Please try again in a little bit.");
    private static final UpholdError UNKNOWN_ERROR = new UpholdError(0, "Unknown Error.");

    private int code;
    private String message;

    private UpholdError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UpholdError getUpholdError(int code) {
        switch (code) {
        case 400:
            return BAD_REQUEST;
        case 401:
            return UNAUTHORIZED;
        case 403:
            return FORBIDDEN;
        case 404:
            return NOT_FOUND;
        case 412:
            return PRECONDITION_FAILED;
        case 416:
            return REQUESTED_RANGE_NOT_SATISFIABLE;
        case 429:
            return TOO_MANY_REQUESTS;
        case 500:
            return INTERNAL_SERVER_ERROR;
        case 503:
            return SERVICE_UNAVAILABLE;
        default:
            return UNKNOWN_ERROR;
        }
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "code = " + code + ", message = " + message;
    }
}
