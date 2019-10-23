package cn.piesat.sanitation.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class FileUtil {

    /**
     * 获取扩展卡路径
     */
    public static String getExCardPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 扩展卡是否存在
     */
    public static boolean isExCardExist() {
        return Environment.getExternalStorageDirectory().exists();
    }

    /**
     * 获取SD卡空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();
        long blocks = stat.getAvailableBlocks();
        long availableSpare = (blocks * blockSize);
        return availableSpare;

    }

    /**
     * 判断文件或文件夹是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExist(String path) {
        boolean exist = false;
        if (null == path) {
            return false;
        }
        try {
            File file = new File(path);
            exist = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 某路径是否存在，不存在则创建 返回 true: 文件夹存在，或创建成功 false: 不存在
     */
    public static boolean openOrCreatDir(String path) {
        File file = new File(path);

        file.isDirectory();

        if (false == file.exists()) {
            return file.mkdirs();
        }

        return true;
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long getFileLength(String path) {
        long fileLength = -1;
        if (path == null || path.length() <= 0) {
            return fileLength;
        } else {
            File file = new File(path);
            try {
                if (file.exists()) {
                    FileInputStream fis = null;
                    fis = new FileInputStream(file);
                    fileLength = fis.available();
                } else {
                    LogUtil.e("info", "文件不存在");
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return fileLength;
    }

    /**
     * 文件重命名
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static File renameFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath); // 要重命名的文件或文件夹
        File newFile = new File(newPath); // 重命名为
        boolean success = oldFile.renameTo(newFile); // 执行重命名
        if (success) {
            return newFile;
        } else {
            return null;
        }
    }

    /**
     * 将流写到文件
     */
    public static boolean saveToFile(InputStream inputStream, String absFileName) {
        LogUtil.e("info", "absFileName==" + absFileName);

        final int FILESIZE = 1024 * 4;
        try {
            FileOutputStream mFileOutputStream = new FileOutputStream(
                    absFileName);
            byte[] buffer = new byte[FILESIZE];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                mFileOutputStream.write(buffer, 0, count);
            }
            mFileOutputStream.flush();
            inputStream.close();

        } catch (Exception e) {
            LogUtil.e("info", "e==" + e);
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * String --> InputStream
     */
    public static InputStream ToStream(String str) {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

    /**
     * InputStream --> String
     */
    public static String ToString(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer.toString();
    }

    /**
     * 删除文件或是整个目录
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFile     源文件File
     * @param destDir     目标目录File
     * @param newFileName 新文件名
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     * @author: suhj 2006-8-31
     */
    public static long copyFile(File srcFile, File destDir, String newFileName) {
        long copySizes = 0;

        if (!srcFile.exists() || !destDir.exists() || null == newFileName) {
            copySizes = -1;
        }

        try {
            FileChannel fcin = new FileInputStream(srcFile).getChannel();
            FileChannel fcout = new FileOutputStream(new File(destDir,
                    newFileName)).getChannel();

            long size = fcin.size();
            fcin.transferTo(0, fcin.size(), fcout);

            fcin.close();
            fcout.close();
            copySizes = size;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return copySizes;
    }

    /**
     * 创建空文件
     */
    public static boolean creatEmptyFile(File file) {
        if (file.length() == 0)
            return true;

        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * 将字节数转换成KB
     */
    public static String byteToKB(long bt) {
        if (bt <= 0) {
            return "0";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(2);
        return df.format(bt / 1024.0) + "KB";
    }

    public static String byteTOString(long bt) {
        if (bt < 1024 * 1024) {
            return byteToKB(bt);
        } else {
            return byteToMB(bt);
        }

    }

    /**
     * 将字节数转换成MB
     */
    public static String byteToMB(long bt) {
        if (bt <= 0) {
            return "0";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(2);
        return df.format(bt / (1024.0 * 1024)) + "M";
    }

    /**
     * 将字符串转成MD5值
     *
     * @param s
     * @return
     */
    public static String stringToMD5(String s) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString().toUpperCase();

    }

    /**
     * 读取txt文件
     * @param filePath
     * @return
     */
    public static String readTxtFile(String filePath) {
        StringBuffer sb = new StringBuffer();
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    sb.append(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }


}
