package cn.piesat.sanitation.ui.activity;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.data.MyAttendanceLBean;
import cn.piesat.sanitation.data.UserListBean;
import cn.piesat.sanitation.model.contract.MineContract;
import cn.piesat.sanitation.model.presenter.MinePresenter;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 考勤列表
 * Created by sen.luo on 2019/10/24.
 */

public class MyAttendanceListActivity extends BaseActivity implements MineContract.MyAttendanceListView{
    @BindView(R.id.tv_title)
    TextView tv_title;


    private MinePresenter minePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_attendance_list;
    }

    @Override
    protected void initView() {
        tv_title.setText("我的考勤");
        minePresenter=new MinePresenter(this);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("userId", BaseApplication.getUserInfo().id);
        map.put("startDate","2019-10-1");
        map.put("endDate","2019-10-30");
        minePresenter.getMyAttendanceList(map);
    }

    @Override
    public void Error(String msg) {
        dismiss();
        ToastUtil.show(this,msg);
    }

    @Override
    public void SuccessOnAttendanceList(MyAttendanceLBean myAttendanceLBean) {
            dismiss();
    }


}
