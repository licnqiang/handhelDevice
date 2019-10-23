package cn.piesat.sanitation.networkdriver.module;

import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.networkdriver.common.BaseReseponseInfo;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 通用接口调用
 */
public interface NetApi {

    @FormUrlEncoded
//  @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("{part}/{methodName}")
    Observable<BaseReseponseInfo> serviceAPI(@Path(value = "part", encoded = true) String part, @Path(value = "methodName", encoded = true) String methodName, @FieldMap() Map<String, String> map);

    @GET("{part}/{methodName}")
    Observable<BaseReseponseInfo> serviceGetAPI(@Path(value = "part", encoded = true) String part, @Path(value = "methodName", encoded = true) String methodName, @QueryMap() Map<String, String> map);

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("{part}/{methodName}")
    Call<BaseReseponseInfo> uploadFilesWithParts(@Path("part") String part, @Path("methodName") String methodName,@Part() List<MultipartBody.Part> parts);


}
