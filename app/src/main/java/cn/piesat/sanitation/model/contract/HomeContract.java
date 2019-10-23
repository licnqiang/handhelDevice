package cn.piesat.sanitation.model.contract;

import cn.piesat.sanitation.data.HomeNewsBean;

/**
 * Created by sen.luo on 2019/10/23.
 */

public interface HomeContract {

    interface HomeNewsView{
        void Error(String msg);
        void SuccessOnNewsList(HomeNewsBean homeNewsBean);
    }

    interface HomeNewsPresenter{
        void GetNewsList();
    }
}
