public class MessageModel {
    public static final int GET_PRODUCT = 1;
    public static final int PUT_PRODUCT = 2;
    public static final int UPDATE_PRODUCT = 3;
    public static final int DELETE_PRODUCT = 4;
    public static final int GET_CUSTOMER = 5;
    public static final int PUT_CUSTOMER = 6;
    public static final int UPDATE_CUSTOMER = 7;
    public static final int DELETE_CUSTOMER = 8;
    public static final int GET_CUSTOMER_BY_USER = 9;
    public static final int GET_ORDER = 10;
    public static final int PUT_ORDER = 11;
    public static final int UPDATE_ORDER = 12;
    public static final int DELETE_ORDER = 13;
    public static final int GET_USER = 14;
    public static final int PUT_USER = 15;
    public static final int REGISTER_USER = 16;
    public static final int GET_ORDER_HISTORY = 17;
    public static final int SEARCH_PRODUCT_BY_PRICE = 18;
    public static final int SEARCH_PRODUCT_BY_NAME = 19;
    public static final int GET_USER_ROW_COUNT = 20;
    public static final int GET_CUSTOMER_ROW_COUNT = 21;
    public static final int GET_ORDER_ROW_COUNT = 22;
    public static final int OPERATION_OK = 23;
    public static final int OPERATION_FAILED = 24;
    public int code;
    public String data;

    public MessageModel() {
        code = 0;
        data = null;
    }

    public MessageModel(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() { return code; }
}