package constants;

public enum ResponseCode {

    WRONG_DATA_TYPE, OBJECT_EXIST, NOT_FOUND_MESSAGE;

    public static final String NOT_FOUND = "Not found";
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
