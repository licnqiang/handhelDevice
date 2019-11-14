package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.HomeNewsBean;
import cn.piesat.sanitation.model.contract.HomeContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * 首页新闻等
 * Created by sen.luo on 2019/10/23.
 */

public class HomePresenter implements ICommonAction,HomeContract.HomeNewsPresenter{


    private CommonPresenter commonPresenter;
    private HomeContract.HomeNewsView homeNewsView;

    public HomePresenter(HomeContract.HomeNewsView homeNewsView) {
        this.homeNewsView = homeNewsView;
        commonPresenter = new CommonPresenter(this);
    }



    @Override
    public void GetNewsList() {
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.new_part, UrlContant.MySourcePart.new_List,
                null, new TypeToken<HomeNewsBean>() {
                });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){
            case UrlContant.MySourcePart.new_List:
                if (status==REQUEST_SUCCESS){
                    HomeNewsBean homeNewsBean= (HomeNewsBean) data;
                    homeNewsView.SuccessOnNewsList(homeNewsBean);
                }else {
                    homeNewsView.Error(Msg);
                }

                break;

        }
    }

}
