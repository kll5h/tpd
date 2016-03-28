package com.tilepay.bitreserveclient.exception;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.http.client.ClientProtocolException;

import com.tilepay.bitreserveclient.model.error.BitreserveError;

public class BitreserveErrorException extends Exception {

    private static final long serialVersionUID = -1629888716270657353L;
    private static final int IO_EXCEPTION_CODE = 1;
    private static final int CLIENT_PROTOCOL_EXCEPTION_CODE = 2;
    private static final int UNSUPPORTED_ENCODING_EXCEPTION_CODE = 3;
    private static final int UNSUPPORTED_CHARSET_EXCEPTION_CODE = 4;

    private int code;

    public BitreserveErrorException(BitreserveError bitreserveError) {
        super(bitreserveError.toString());
        this.code = bitreserveError.getCode();
    }

    public BitreserveErrorException(IOException exception) {
        super("code = " + IO_EXCEPTION_CODE + ", message = " + exception.getMessage());
        this.code = IO_EXCEPTION_CODE;
    }

    public BitreserveErrorException(ClientProtocolException exception) {
        super("code = " + CLIENT_PROTOCOL_EXCEPTION_CODE + ", message = " + exception.getMessage());
        this.code = CLIENT_PROTOCOL_EXCEPTION_CODE;
    }

    public BitreserveErrorException(UnsupportedEncodingException exception) {
        super("code = " + UNSUPPORTED_ENCODING_EXCEPTION_CODE + ", message = " + exception.getMessage());
        this.code = UNSUPPORTED_ENCODING_EXCEPTION_CODE;
    }
    
    public BitreserveErrorException(UnsupportedCharsetException exception) {
        super("code = " + UNSUPPORTED_CHARSET_EXCEPTION_CODE + ", message = " + exception.getMessage());
        this.code = UNSUPPORTED_CHARSET_EXCEPTION_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}