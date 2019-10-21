package cn.piesat.sanitation.constant;

import android.os.Environment;

import java.io.File;


/**
 * @author lq
 * @fileName FileConstant
 * @data on  2019/2/27 16:51
 * @describe TODO
 */
public class FileConstant {
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

    /**
     * 手机根目录
     * @return
     */
    public static String getRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(STR_SDCARD_ROOT).append(File.separator);
        return hasFileAndCreate(sb.toString());
    }

    /**
     * 数据库目录
     * @param userId   根据用户id 找相应的数据库
     * @return
     */
    public static String getDBPath(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootPath()).append(File.separator)
                .append(SysContant.SystemContats.PROJECT_NAME).append(File.separator)
                .append(DB_NAME).append(File.separator)
                .append(userId);
        return hasFileAndCreate(sb.toString());
    }


}
