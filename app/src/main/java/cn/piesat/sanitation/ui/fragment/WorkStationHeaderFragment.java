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
import cn.piesat.sanitation.ui.activity.AccidentReportActivity;
import cn.piesat.sanitation.ui.activity.AssignOrderActivity;
import cn.piesat.sanitation.ui.activity.CarBreakDownActivity;
import cn.piesat.sanitation.ui.activity.CarMaintenanceActivity;
import cn.piesat.sanitation.ui.activity.EventReportActivity;
import cn.piesat.sanitation.ui.activity.GasoLineReportActivity;
import cn.piesat.sanitation.ui.activity.OrderListActivity;
import cn.piesat.sanitation.ui.activity.StationHeaderApproveActivity;
import cn.piesat.sanitation.ui.activity.ViolateReportActivity;
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



    @OnClick({R.id.bai_dan, R.id.yun_dan, R.id.event_report, R.id.xingzhegn_shenpi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bai_dan:
                toActivity(AssignOrderActivity.class);
                break;
            case R.id.yun_dan:
                toActivity(OrderListActivity.class);
                break;
            case R.id.event_report:
                toActivity(EventReportActivity.class);
                break;
            case R.id.xingzhegn_shenpi:
                toActivity(StationHeaderApproveActivity.class);
                break;
        }
    }
}
