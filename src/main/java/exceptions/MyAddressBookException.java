package exceptions;

import constants.ResponseCode;

public class MyAddressBookException extends Exception {
    private ResponseCode code;
    private String message;
    public static final String NOT_FOUND_MESSAGE = "Contact not found";


    public MyAddressBookException(ResponseCode code) {
        this.code = code;
    }

    public MyAddressBookException(ResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyAddressBookException() {
        this.code = ResponseCode.NOT_FOUND;
        this.message = NOT_FOUND_MESSAGE;
    }

    public ResponseCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
