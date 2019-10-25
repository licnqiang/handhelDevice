package cn.piesat.sanitation.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.ui.activity.CarFaultActivity;
import cn.piesat.sanitation.ui.activity.CarSerViceActivity;
import cn.piesat.sanitation.ui.activity.DriverOrderListActivity;
import cn.piesat.sanitation.ui.activity.ExpenseAccountActivity;

/**
 * 司机
 */
public class WorkDriverFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_driver;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.zhixingdingdan, R.id.car_fault, R.id.money_report, R.id.car_service, R.id.one_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhixingdingdan:
                toActivity(DriverOrderListActivity.class);
                break;
            case R.id.car_fault:
                toActivity(CarFaultActivity.class);
                break;
            case R.id.money_report:
                toActivity(ExpenseAccountActivity.class);
                break;
            case R.id.car_service:
                toActivity(CarSerViceActivity.class);
                break;
            case R.id.one_help:
                break;
        }
    }
}
