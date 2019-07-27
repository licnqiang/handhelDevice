package cn.piesat.retrofitframe.constant;

/**
 * 用于服务器的切换
 */
public class UrlConfig {

    private final static String U1 = "http://112.113.96.30:8080/hydrology/";

    // 正式服务器
    private final static String U2 = "http://112.113.96.30:8080/hydrology/";

    // 测试版本下 正式测试服务器地址切换用
    public static boolean flag = true;


    //切换服务器
    public static final String getURLPreFix() {
        return flag ? U1 : U2;
    }
}
