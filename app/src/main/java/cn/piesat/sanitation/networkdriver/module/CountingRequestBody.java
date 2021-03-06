package cn.piesat.sanitation.networkdriver.module;

import java.io.IOException;

import cn.piesat.sanitation.networkdriver.upLoadFile.UploadListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @author lq
 * @fileName CountingRequestBody
 * @data on  2019/2/19 15:48
 * @describe 获取上传进度
 */
public class CountingRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private UploadListener mUploadListener;
    private CountingSink mCountingSink;

    public CountingRequestBody(RequestBody requestBody, UploadListener uploadListener) {
        mRequestBody = requestBody;
        mUploadListener = uploadListener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return mRequestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;

        mCountingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(mCountingSink);

        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            mUploadListener.onRequestProgress(bytesWritten, contentLength());
        }
    }
}
