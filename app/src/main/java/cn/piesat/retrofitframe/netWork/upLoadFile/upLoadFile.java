package cn.piesat.retrofitframe.netWork.upLoadFile;

import java.util.List;
import cn.piesat.retrofitframe.netWork.common.BaseReseponseInfo;
import cn.piesat.retrofitframe.netWork.module.NetApi;
import cn.piesat.retrofitframe.netWork.module.RetrofitUtils;
import cn.piesat.retrofitframe.util.Log;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class upLoadFile extends RetrofitUtils {

    protected NetApi service = getRetrofit().create(NetApi.class);

    public interface ResultCallBack {
        void succeed(Object str);

        void faild();
    }

    /**
     * 文件上传
     *
     * @param paths
     */
    public  void uploadFile(String part,String methodName,List<String> paths, final ResultCallBack resultCallBack) {
        final StringBuffer sbServerPath = new StringBuffer();
        List<MultipartBody.Part> body = UpLoadFileNet.filesToMultipartBodyParts(paths);
        Call<BaseReseponseInfo> call = service.uploadFilesWithParts(part,methodName,body);
        call.enqueue(new Callback<BaseReseponseInfo>() {
            @Override
            public void onResponse(Call<BaseReseponseInfo> call, Response<BaseReseponseInfo> response) {
                if (!response.isSuccessful()) {
                    resultCallBack.faild();
                    return;
                }
                BaseReseponseInfo fileInfo = response.body();

                if(null!=fileInfo) {
                    resultCallBack.succeed(fileInfo.data);
                }else {
                    resultCallBack.faild();
                }
            }
            @Override
            public void onFailure(Call<BaseReseponseInfo> call, Throwable t) {
                Log.e("-----------","---文件上传失败-------"+t.getMessage());
                resultCallBack.faild();
            }
        });
    }
}
