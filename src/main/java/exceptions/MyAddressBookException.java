package exceptions;

import constants.ResponseCode;
import entity.Contact;

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

    public MyAddressBookException(Contact contact) {
        System.out.println("Contact is present with ID " + contact.getId());
    }

    public ResponseCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
