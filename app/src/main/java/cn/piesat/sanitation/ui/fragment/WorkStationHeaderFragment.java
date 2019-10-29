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
import cn.piesat.sanitation.ui.activity.AssignOrderActivity;
import cn.piesat.sanitation.ui.activity.ExpenseAccountActivity;
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


    @OnClick({R.id.bai_dan, R.id.yun_dan, R.id.money_report,R.id.refuse_transport,R.id.record_refuse_transport,R.id.record_driver_mody})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refuse_transport:     //审批 垃圾运输
                break;
            case R.id.bai_dan:              //运单
                toActivity(AssignOrderActivity.class);
                break;
            case R.id.money_report:         //行政报销
                toActivity(ExpenseAccountActivity.class);
                break;
            case R.id.yun_dan:              //运单
                toActivity(OrderListActivity.class);
                break;
            case R.id.record_refuse_transport:              //记录 垃圾运输
                break;
            case R.id.record_driver_mody:              //记录  设备维护
                break;
        }
    }
}
