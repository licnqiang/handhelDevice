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
    private static final String ROOT_FILE_NAME = SysContant.SystemContats.PROJECT_NAME;
    public static final String DB_NAME = "DB";
    private static final String FILE_DOWNLOAD_NAME = "FileDownload";
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
        sb.append(ROOT_FILE_NAME);
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
//                .append(SysContant.SystemContats.PROJECT_NAME).append(File.separator)
                .append(DB_NAME)
                .append(userId);
        return hasFileAndCreate(sb.toString());
    }

    public static String getFileDownloadPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootPath()).append(File.separator);
        sb.append(FILE_DOWNLOAD_NAME);
        return hasFileAndCreate(sb.toString());
    }

}
