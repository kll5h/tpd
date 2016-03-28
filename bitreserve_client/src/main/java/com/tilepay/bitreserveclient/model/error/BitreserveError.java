package com.tilepay.bitreserveclient.model.error;

public class BitreserveError {

	private static final BitreserveError BAD_REQUEST = new BitreserveError(400, "Bad Request – Validation failed.");
	private static final BitreserveError UNAUTHORIZED = new BitreserveError(401, "Unauthorized – Bad credentials.");
	private static final BitreserveError FORBIDDEN = new BitreserveError(403, "Forbidden – Access forbidden.");
	private static final BitreserveError NOT_FOUND = new BitreserveError(404, "Not Found – Object not found.");
	private static final BitreserveError PRECONDITION_FAILED = new BitreserveError(412, "Precondition Failed.");
	private static final BitreserveError REQUESTED_RANGE_NOT_SATISFIABLE = new BitreserveError(416, "Requested Range Not Satisfiable.");
	private static final BitreserveError TOO_MANY_REQUESTS = new BitreserveError(429, "Too Many Requests – Rate limit exceeded.");
	private static final BitreserveError INTERNAL_SERVER_ERROR = new BitreserveError(500, "Internal Server Error – Something went wrong in our server.");
	private static final BitreserveError SERVICE_UNAVAILABLE = new BitreserveError(503, "Unavailable – We’re temporarily offline for maintenance. Please try again in a little bit.");
	private static final BitreserveError UNKNOWN_ERROR = new BitreserveError(0, "Unknown Error.");

	private int code;
	private String message;

	private BitreserveError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static BitreserveError getBitreserveError(int code) {
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
