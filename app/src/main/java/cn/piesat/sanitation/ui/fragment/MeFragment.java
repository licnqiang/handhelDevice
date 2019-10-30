package cn.piesat.sanitation.ui.fragment;



import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.ui.activity.MyAttendanceListActivity;
import cn.piesat.sanitation.ui.activity.MyDetailActivity;
import cn.piesat.sanitation.ui.activity.SplashActivity;
import cn.piesat.sanitation.ui.activity.StationAllUserActivity;
import cn.piesat.sanitation.ui.activity.UserListActivity;
import cn.piesat.sanitation.util.DialogUtils;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.UiUtils;

/**
 * 个人中心
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvHead)
    TextView tvHead;
    @BindView(R.id.tvAttendance)
    TextView tvAttendance;

 /*   @BindView(R.id.tvUserUnit)
    TextView tvUserUnit;*/

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        if (BaseApplication.getUserInfo()==null){
            return;
        }
        if (BaseApplication.getUserInfo().name!=null){
            String name=BaseApplication.getUserInfo().name;
            tvUserName.setText(name);
            tvHead.setText(UiUtils.getPinYinHeadChar(name.substring(0,1)));
        }

        if (BaseApplication.getUserInfo().userType==4){
            tvAttendance.setText("考勤查询");
        }else {
            tvAttendance.setText("我的考勤");
        }


/*        int type =BaseApplication.getUserInfo().userType;
        switch (type){
            case 1:
                tvUserUnit.setText("管理员");
                break;
            case 2:
                tvUserUnit.setText("普通用户");
                break;
            case 3:
                tvUserUnit.setText("环卫集团员工");
                break;
            case 4:
                tvUserUnit.setText("站长");
                break;
            case 5:
                tvUserUnit.setText("操作工");
                break;
            case 6:
                tvUserUnit.setText("扫保人员");
                break;
            case 7:
                tvUserUnit.setText("司机");
                break;
            default:
                tvUserUnit.setText("普通用户");
                break;
        }*/

    }

    @OnClick({R.id.imgExit,R.id.layoutAttendance,R.id.layoutEdit,R.id.layoutModify})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.imgExit:
                DialogUtils.generalDialog(getActivity(), "确认退出当前账号吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpHelper.clearAllValue(getActivity());
                        startActivity(new Intent(getActivity(), SplashActivity.class));
                        getActivity().finish();
                    }
                });
                break;
            case R.id.layoutAttendance:
                //站长角色先查询人员
                if (BaseApplication.getUserInfo().userType==4){
                    startActivity(new Intent(getActivity(), UserListActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), MyAttendanceListActivity.class));
                }
                break;
            case R.id.layoutEdit:
                toActivity(MyDetailActivity.class);
                break;
            case R.id.layoutModify: //我的上报
                toActivity(StationAllUserActivity.class);
                break;
        }
    }

    @Override
    protected void initData() {
    }


}
