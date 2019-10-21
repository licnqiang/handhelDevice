package cn.piesat.sanitation.constant;

/**
 * 用于服务器的切换
 */
public class IPConfig {

    //自己服务器本地服务器
    private final static String U1 = "http://112.113.96.30:8080/hydrology/";

    //自己服务器正式服务器
    private final static String U2 = "http://112.113.96.30:8080/hydrology/";

    // 测试版本下 正式测试服务器地址切换用
    public static boolean flag = true;

    // 外协服务器
    private final static String U3 = "http://192.168.1.18:8080/hydrology/";


    //切换自己服务器
    public static final String getURLPreFix() {
        return flag ? U1 : U2;
    }

    //切换外协服务器
    public static final String getOutSourceURLPreFix() {
        return U3;
    }

}
