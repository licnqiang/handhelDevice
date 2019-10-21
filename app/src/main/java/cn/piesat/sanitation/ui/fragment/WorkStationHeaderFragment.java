package cn.piesat.sanitation.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.ui.activity.OrderListActivity;
import cn.piesat.sanitation.ui.view.MyWorkModul;

/**
 * 工作
 */
public class WorkStationHeaderFragment extends BaseFragment {

    @BindView(R.id.bai_dan)
    MyWorkModul baiDan;
    @BindView(R.id.yun_dan)
    MyWorkModul yunDan;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_station_header;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.bai_dan, R.id.yun_dan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bai_dan:
                break;
            case R.id.yun_dan:
                toActivity(OrderListActivity.class);
                break;
        }
    }
}
