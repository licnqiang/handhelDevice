package cn.piesat.retrofitframe.networkdriver.upLoadFile;

/**
 * @author lq
 * @fileName UploadListener
 * @data on  2019/2/19 15:49
 * @describe 文件上传进度回调接口
 */
public interface UploadListener {
    void onRequestProgress(long bytesWritten, long contentLength);
}
