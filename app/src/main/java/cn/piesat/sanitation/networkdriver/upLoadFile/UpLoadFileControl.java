package cn.piesat.sanitation.networkdriver.upLoadFile;

import java.util.List;

import cn.piesat.sanitation.networkdriver.common.BaseReseponseInfo;
import cn.piesat.sanitation.networkdriver.module.NetApi;
import cn.piesat.sanitation.networkdriver.module.RetrofitUtils;
import cn.piesat.sanitation.util.LogUtil;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpLoadFileControl extends RetrofitUtils {

    public interface ResultCallBack {
        void succeed(Object str);

        void faild();
    }

    /**
     * 文件上传
     *
     * @param paths
     */
    public static void uploadFile(boolean switchService,String part, String methodName, List<String> paths,UploadListener uploadListener, final ResultCallBack resultCallBack) {
        List<MultipartBody.Part> body = UpLoadFileNet.filesToMultipartBodyParts(paths);
        Call<BaseReseponseInfo> call = getRetrofit(switchService,uploadListener).create(NetApi.class).uploadFilesWithParts(part, methodName, body);
        call.enqueue(new Callback<BaseReseponseInfo>() {
            @Override
            public void onResponse(Call<BaseReseponseInfo> call, Response<BaseReseponseInfo> response) {
                if (!response.isSuccessful()) {
                    resultCallBack.faild();
                    return;
                }
                BaseReseponseInfo fileInfo = response.body();

                if (null != fileInfo) {
                    resultCallBack.succeed(fileInfo.data);
                } else {
                    resultCallBack.faild();
                }
            }

            @Override
            public void onFailure(Call<BaseReseponseInfo> call, Throwable t) {
                LogUtil.e("-----------", "---文件上传失败-------" + t.getMessage());
                resultCallBack.faild();
            }
        });
    }
}
