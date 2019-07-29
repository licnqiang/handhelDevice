package cn.piesat.retrofitframe.networkdriver.upLoadFile;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author lq
 * @fileName MediaFile
 * @data on  2019/7/26 9:41
 * @describe TODO
 */
public class MediaFile {
    private static final HashMap<String, MediaFile.MediaFileType> sFileTypeMap = new HashMap();
    private static final HashMap<String, Integer> sMimeTypeMap = new HashMap();
    private static final HashMap<String, Integer> sFileTypeToFormatMap = new HashMap();
    private static final HashMap<String, Integer> sMimeTypeToFormatMap = new HashMap();
    private static final HashMap<Integer, String> sFormatToMimeTypeMap = new HashMap();

    public MediaFile() {
    }

    static void addFileType(String extension, int fileType, String mimeType) {
        sFileTypeMap.put(extension, new MediaFile.MediaFileType(fileType, mimeType));
        sMimeTypeMap.put(mimeType, fileType);
    }

    static void addFileType(String extension, int fileType, String mimeType, int mtpFormatCode) {
        addFileType(extension, fileType, mimeType);
        sFileTypeToFormatMap.put(extension, mtpFormatCode);
        sMimeTypeToFormatMap.put(mimeType, mtpFormatCode);
        sFormatToMimeTypeMap.put(mtpFormatCode, mimeType);
    }

    public static boolean isAudioFileType(int fileType) {
        return fileType >= 1 && fileType <= 10 || fileType >= 11 && fileType <= 13;
    }

    public static boolean isVideoFileType(int fileType) {
        return fileType >= 21 && fileType <= 30 || fileType >= 200 && fileType <= 200;
    }

    public static boolean isImageFileType(int fileType) {
        return fileType >= 31 && fileType <= 36;
    }

    public static boolean isPlayListFileType(int fileType) {
        return fileType >= 41 && fileType <= 44;
    }

    public static boolean isDrmFileType(int fileType) {
        return fileType >= 51 && fileType <= 51;
    }

    public static MediaFile.MediaFileType getFileType(String path) {
        int lastDot = path.lastIndexOf(46);
        return lastDot < 0 ? null : (MediaFile.MediaFileType)sFileTypeMap.get(path.substring(lastDot + 1).toUpperCase(Locale.ROOT));
    }

    public static boolean isMimeTypeMedia(String mimeType) {
        int fileType = getFileTypeForMimeType(mimeType);
        return isAudioFileType(fileType) || isVideoFileType(fileType) || isImageFileType(fileType) || isPlayListFileType(fileType);
    }

    public static String getFileTitle(String path) {
        int lastSlash = path.lastIndexOf(47);
        if (lastSlash >= 0) {
            ++lastSlash;
            if (lastSlash < path.length()) {
                path = path.substring(lastSlash);
            }
        }

        int lastDot = path.lastIndexOf(46);
        if (lastDot > 0) {
            path = path.substring(0, lastDot);
        }

        return path;
    }

    public static int getFileTypeForMimeType(String mimeType) {
        Integer value = (Integer)sMimeTypeMap.get(mimeType);
        return value == null ? 0 : value;
    }

    public static String getMimeTypeForFile(String path) {
        MediaFile.MediaFileType mediaFileType = getFileType(path);
        return mediaFileType == null ? null : mediaFileType.mimeType;
    }

    public static int getFormatCode(String fileName, String mimeType) {
        if (mimeType != null) {
            Integer value = (Integer)sMimeTypeToFormatMap.get(mimeType);
            if (value != null) {
                return value;
            }
        }

        int lastDot = fileName.lastIndexOf(46);
        if (lastDot > 0) {
            String extension = fileName.substring(lastDot + 1).toUpperCase(Locale.ROOT);
            Integer value = (Integer)sFileTypeToFormatMap.get(extension);
            if (value != null) {
                return value;
            }
        }

        return 12288;
    }

    public static String getMimeTypeForFormatCode(int formatCode) {
        return (String)sFormatToMimeTypeMap.get(formatCode);
    }

    static {
        addFileType("MP3", 1, "audio/mp3", 12297);
        addFileType("MPGA", 1, "audio/mpeg", 12297);
        addFileType("M4A", 2, "audio/mp4", 12299);
        addFileType("WAV", 3, "audio/x-wav", 12296);
        addFileType("AMR", 4, "audio/amr");
        addFileType("AWB", 5, "audio/amr-wb");
        addFileType("OGG", 7, "audio/ogg", 47362);
        addFileType("OGG", 7, "application/ogg", 47362);
        addFileType("OGA", 7, "application/ogg", 47362);
        addFileType("AAC", 8, "audio/aac", 47363);
        addFileType("AAC", 8, "audio/aac-adts", 47363);
        addFileType("MKA", 9, "audio/x-matroska");
        addFileType("MID", 11, "audio/midi");
        addFileType("MIDI", 11, "audio/midi");
        addFileType("XMF", 11, "audio/midi");
        addFileType("RTTTL", 11, "audio/midi");
        addFileType("SMF", 12, "audio/sp-midi");
        addFileType("IMY", 13, "audio/imelody");
        addFileType("RTX", 11, "audio/midi");
        addFileType("OTA", 11, "audio/midi");
        addFileType("MXMF", 11, "audio/midi");
        addFileType("MPEG", 21, "video/mpeg", 12299);
        addFileType("MPG", 21, "video/mpeg", 12299);
        addFileType("MP4", 21, "video/mp4", 12299);
        addFileType("M4V", 22, "video/mp4", 12299);
        addFileType("3GP", 23, "video/3gpp", 47492);
        addFileType("3GPP", 23, "video/3gpp", 47492);
        addFileType("3G2", 24, "video/3gpp2", 47492);
        addFileType("3GPP2", 24, "video/3gpp2", 47492);
        addFileType("MKV", 27, "video/x-matroska");
        addFileType("WEBM", 30, "video/webm");
        addFileType("TS", 28, "video/mp2ts");
        addFileType("AVI", 29, "video/avi");
        addFileType("JPG", 31, "image/jpeg", 14337);
        addFileType("JPEG", 31, "image/jpeg", 14337);
        addFileType("GIF", 32, "image/gif", 14343);
        addFileType("PNG", 33, "image/png", 14347);
        addFileType("BMP", 34, "image/x-ms-bmp", 14340);
        addFileType("WBMP", 35, "image/vnd.wap.wbmp");
        addFileType("WEBP", 36, "image/webp");
        addFileType("M3U", 41, "audio/x-mpegurl", 47633);
        addFileType("M3U", 41, "application/x-mpegurl", 47633);
        addFileType("PLS", 42, "audio/x-scpls", 47636);
        addFileType("WPL", 43, "application/vnd.ms-wpl", 47632);
        addFileType("M3U8", 44, "application/vnd.apple.mpegurl");
        addFileType("M3U8", 44, "audio/mpegurl");
        addFileType("M3U8", 44, "audio/x-mpegurl");
        addFileType("FL", 51, "application/x-android-drm-fl");
        addFileType("TXT", 100, "text/plain", 12292);
        addFileType("HTM", 101, "text/html", 12293);
        addFileType("HTML", 101, "text/html", 12293);
        addFileType("PDF", 102, "application/pdf");
        addFileType("DOC", 104, "application/msword", 47747);
        addFileType("XLS", 105, "application/vnd.ms-excel", 47749);
        addFileType("PPT", 106, "application/mspowerpoint", 47750);
        addFileType("FLAC", 10, "audio/flac", 47366);
        addFileType("ZIP", 107, "application/zip");
        addFileType("MPG", 200, "video/mp2p");
        addFileType("MPEG", 200, "video/mp2p");
    }

    public static class MediaFileType {
        public final int fileType;
        public final String mimeType;

        MediaFileType(int fileType, String mimeType) {
            this.fileType = fileType;
            this.mimeType = mimeType;
        }
    }

}
