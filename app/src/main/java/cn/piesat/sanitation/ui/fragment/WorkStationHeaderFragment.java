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
import cn.piesat.sanitation.ui.activity.InsuranceActivity;
import cn.piesat.sanitation.ui.activity.MaintainActivity;
import cn.piesat.sanitation.ui.activity.OrderListActivity;
import cn.piesat.sanitation.ui.activity.StationHeaderApproveActivity;
import cn.piesat.sanitation.ui.activity.UpkeepActivity;
import cn.piesat.sanitation.ui.activity.ViolateReportActivity;
import cn.piesat.sanitation.ui.view.MyWorkModul;

/**
 * 工作
 */
public class WorkStationHeaderFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_station_header_new;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }



//    @OnClick({R.id.bai_dan, R.id.yun_dan, R.id.event_report, R.id.xingzhegn_shenpi})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.bai_dan:
//                toActivity(AssignOrderActivity.class);
//                break;
//            case R.id.yun_dan:
//                toActivity(OrderListActivity.class);
//                break;
//            case R.id.event_report:
//                toActivity(EventReportActivity.class);
//                break;
//            case R.id.xingzhegn_shenpi:
//                toActivity(StationHeaderApproveActivity.class);
//                break;
//        }
//    }


    @OnClick({R.id.ll_paidan, R.id.ll_yundan, R.id.item_weigui, R.id.item_shigu, R.id.item_jiayou, R.id.item_nianjian, R.id.item_weixiu, R.id.item_baoyang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_paidan:
                toActivity(AssignOrderActivity.class);
                break;
            case R.id.ll_yundan:
                toActivity(OrderListActivity.class);
                break;
            case R.id.item_weigui:
                toActivity(ViolateReportActivity.class);
                break;
            case R.id.item_shigu:
                toActivity(AccidentReportActivity.class);
                break;
            case R.id.item_jiayou:
                toActivity(GasoLineReportActivity.class);  //加油列表
                break;
            case R.id.item_nianjian:
                toActivity(InsuranceActivity.class);
                break;
            case R.id.item_weixiu:
                toActivity(MaintainActivity.class);
                break;
            case R.id.item_baoyang:
                toActivity(UpkeepActivity.class);
                break;
        }
    }
}
