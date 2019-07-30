package cn.piesat.retrofitframe.util;


import cn.piesat.retrofitframe.BuildConfig;

/**
 * 日志管理�?
 *
 * @author lq
 */
public class Log {

    private static boolean flag = BuildConfig.DEBUG;

    /**
     * 该log没有字节长度限制
     *
     * @param name
     * @param value
     */
    public static void e(String name, String value) {
        if (flag) {
            int max_str_length = 2001 - name.length();
            //大于4000时
            while (value.length() > max_str_length) {

                android.util.Log.e(name, value.substring(0, max_str_length));

                value = value.substring(max_str_length);
            }

            android.util.Log.e(name, value);
        }
    }
}
