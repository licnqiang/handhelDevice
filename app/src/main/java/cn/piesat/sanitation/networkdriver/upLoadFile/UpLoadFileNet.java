package cn.piesat.sanitation.networkdriver.upLoadFile;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author lq
 * @time 2018/11/29
 */
public class UpLoadFileNet {

    private static RequestBody requestBody;

    public static MultipartBody filesToMultipartBody(List<String> paths) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String path : paths) {
            File file = new File(path);
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<String> paths) {
        File file;
        List<MultipartBody.Part> parts = new ArrayList<>(paths.size());
        for (String path : paths) {
            file = new File(path);
            if (null == file || !file.isFile()) {
                return null;
            }
            String mediaType = "multipart/form-data;charset=UTF-8";
            MediaFile.MediaFileType fileType = MediaFile.getFileType(file.getAbsolutePath());
            if (fileType != null) {
                mediaType = fileType.mimeType;
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


}
