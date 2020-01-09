package cn.piesat.sanitation.ui.fragment;


import android.view.View;

import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.ui.activity.EventReportActivity;
import cn.piesat.sanitation.ui.activity.StationHeaderApproveActivity;

/**
 * 集团人员工作
 */
public class WorkGroupFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_group;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.event_report, R.id.xingzhegn_shenpi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.event_report:
                toActivity(EventReportActivity.class);
                break;
            case R.id.xingzhegn_shenpi:
                toActivity(StationHeaderApproveActivity.class);
                break;
        }
    }
}
