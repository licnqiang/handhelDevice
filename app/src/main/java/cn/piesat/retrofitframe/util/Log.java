package cn.piesat.retrofitframe.util;
/**
 * 日志管理�?
 * 
 * @author jigsaw
 */
public class Log {
	
	private static boolean flag = true;

    public static void e(String name, String value) {
        if (flag) {
            android.util.Log.e(name, value);
        }
    }
    public static void i(String name, String value) {
    	if (flag) {
    		android.util.Log.i(name, value);
    	}
    }
	
}
