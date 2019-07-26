package cn.piesat.retrofitframe.constant;

import android.os.Environment;

import java.io.File;


/**
 * @author lq
 * @fileName FileConstant
 * @data on  2019/2/27 16:51
 * @describe TODO
 */
public class FileConstant {
    public static final String PROJECT_NAME = "retrofitframe";
    public static final String DB_NAME = "DB";
    public static String STR_SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String hasFileAndCreate(String filePath) {
        if (filePath != null && !filePath.equals("")) {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return filePath;
    }

    public static String getRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(STR_SDCARD_ROOT).append(File.separator);
        return hasFileAndCreate(sb.toString());
    }

    public static String getDBPath(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootPath()).append(File.separator)
                .append(PROJECT_NAME).append(File.separator)
                .append(DB_NAME).append(File.separator)
                .append(userId);
        return hasFileAndCreate(sb.toString());
    }


}
