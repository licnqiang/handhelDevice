package cn.piesat.sanitation.util;


/**
 * @author lq
 * @fileName NetUtil
 * @data on  2019/7/23 11:07
 * @describe TODO
 */
public class NetUtil {

    /**
     * 没有网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;



    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public static boolean isNetConnect(int netType) {
        if (netType == 1) {
            return true;
        } else if (netType == 0) {
            return true;
        } else if (netType == -1) {
            return false;
        }
        return false;
    }

}
