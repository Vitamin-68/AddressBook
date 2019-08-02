package constants;

public enum ResponseCode {
    WRONG_DATA_TYPE;

    private int code;
    public int numberOfContacts = 10;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getNumberOfContacts() {
        return numberOfContacts;
    }
}
