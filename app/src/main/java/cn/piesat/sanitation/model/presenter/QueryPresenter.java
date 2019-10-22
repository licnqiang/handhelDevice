package cn.piesat.sanitation.model.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.model.contract.QueryContract;
import cn.piesat.sanitation.networkdriver.common.BasePresenter;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.Log;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;
import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS_TWO;

/**
 * @author lq
 * @fileName 查询压缩站
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class QueryPresenter implements ICommonAction, QueryContract.QueryPresenter {

    private CommonPresenter commonPresenter;
    private QueryContract.QueryView QueryView;

    public QueryPresenter(QueryContract.QueryView queryView) {
        QueryView = queryView;
        commonPresenter = new CommonPresenter(this);
    }


    @Override
    public void QueryCompress(int pageNum, int pageSize) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        commonPresenter.invokeInterfaceObtainData(false, false, false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.query_compress_station,
                hashMap, new TypeToken<CompressStations>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            //查询压缩站
            case UrlContant.OutSourcePart.query_compress_station:
                if (status == REQUEST_SUCCESS_TWO) {
                    CompressStations compressStations = (CompressStations) data;
                    QueryView.SuccessFinshByCompress(compressStations);
                } else {
                    QueryView.Error(Msg);
                }
                break;
        }


    }

}
