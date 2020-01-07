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
import cn.piesat.sanitation.ui.activity.CarBreakDownActivity;
import cn.piesat.sanitation.ui.activity.CarMaintenanceActivity;
import cn.piesat.sanitation.ui.activity.ExpenseAccountActivity;
import cn.piesat.sanitation.ui.activity.GasoLineReportActivity;
import cn.piesat.sanitation.ui.activity.OrderListActivity;
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


    @OnClick({R.id.bai_dan, R.id.yun_dan, R.id.money_report, R.id.refuse_transport, R.id.record_driver_mody,R.id.record_car_breakdown,R.id.add_gaso_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.refuse_transport:     //审批 违章上报
                toActivity(ViolateReportActivity.class);
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
            case R.id.record_driver_mody:              //记录  设备维护
                toActivity(CarMaintenanceActivity.class);  //车辆维保列表
                break;
            case R.id.record_car_breakdown:              //记录  车辆故障
                toActivity(CarBreakDownActivity.class);  //车辆故障列表
                break;
            case R.id.add_gaso_line:              //记录  加油上报
                toActivity(GasoLineReportActivity.class);  //加油列表
                break;
        }
    }
}
