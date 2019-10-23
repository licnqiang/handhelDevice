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
import cn.piesat.sanitation.ui.activity.SplashActivity;
import cn.piesat.sanitation.util.DialogUtils;
import cn.piesat.sanitation.util.SpHelper;

/**
 * 个人中心
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserUnit)
    TextView tvUserUnit;

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
            tvUserName.setText(BaseApplication.getUserInfo().name);
        }
        String type =BaseApplication.getUserInfo().userType;
        switch (type){
            case "1":
                tvUserUnit.setText("管理员");
                break;
            case "2":
                tvUserUnit.setText("普通用户");
                break;
            case "3":
                tvUserUnit.setText("环卫集团员工");
                break;
            case "4":
                tvUserUnit.setText("站长");
                break;
            case "5":
                tvUserUnit.setText("操作工");
                break;
            case "6":
                tvUserUnit.setText("扫保人员");
                break;
            case "7":
                tvUserUnit.setText("司机");
                break;
            default:
                tvUserUnit.setText("普通用户");
                break;
        }

    }

    @OnClick({R.id.btExit})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btExit:
                DialogUtils.generalDialog(getActivity(), "确认退出当前账号吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpHelper.clearAllValue(getActivity());
                        startActivity(new Intent(getActivity(), SplashActivity.class));
                        getActivity().finish();
                    }
                });
                break;
        }
    }

    @Override
    protected void initData() {
    }


}
