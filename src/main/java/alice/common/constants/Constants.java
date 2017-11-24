package alice.common.constants;

public final class Constants {



    private Constants(){}
    
    public static final int CODE_SUCCESS = 10000;
    
    public static final int CODE_FAILED = 20000;
    public static final String SAVE_FAILURE = "Save failed.";
    public static final String SAVE_SUCCESS = "Save success.";
    public static final String SEND_SUCCESS = "Send success.";

    public static final int SEND_SUCCESS_TYPE = 1;
    public static final int SEND_FAILURE_TYPE = 0;

    public static final String TESTCASE_TYPE_WS = "WS";
    public static final String TESTCASE_TYPE_UI = "UI";
    public static final String TESTCASE_TYPE_UT = "UT";

    /**
     * 有效数据
     */
    public static final int DB_DATA_ACTIVE = 1;
    /**
     * 无效数据
     */
    public static final int DB_DATA_INVALID = 0;

    public static final String XXD = "XXD";

    public static final String WS_GET = "GET";
    public static final String WS_POST = "POST";
    public static final String WS_PATCH = "PATCH";
    public static final String WS_PUT = "PUT";

    public static final String REQUEST_HEADERS_CLIENTID = "clientId";
    public static final String REQUEST_HEADERS_CLIENTTIME = "clientTime";
    public static final String JSON_TEMPLATE_HEADERS_S = "s";
    public static final String REQUEST_HEADERS_CONTENTTYPE = "Content-Type";

    public static final String FUNCTION_REPORT_PATH = "/usr/local/tomcat/webapps/function";
    public static final String TOMCAT_URL = "http://192.168.33.47:8080";


//    public static final String FUNCTION_REPORT_PATH = "/Users/zhangli/Documents/apiPDemo";
//    public static final String TOMCAT_URL = "http://172.16.15.88:8080";

    public static final int DB_DATA_REPETITION = 100111;

}
