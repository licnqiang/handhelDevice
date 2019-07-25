package cn.piesat.retrofitframe.netWork.configuration;

/**
 * 用于服务器的切换
 */
public class UrlConfig {

    // 测试服务器  192.168.0.145/8080  119.23.147.122/  http://123.206.31.206/
    private final static String U1 = "http://112.113.96.30:8080/hydrology/";

    // 正式服务器
    private final static String U2 = "http://112.113.96.30:8080/hydrology/";

    // 测试版本下 正式测试服务器地址切换用
    public static boolean flag = true;


    //切换服务器
    public static final String getURLPreFix() {
        if (flag) {
            return U1;
        } else {
            return U2;
        }
    }
}
