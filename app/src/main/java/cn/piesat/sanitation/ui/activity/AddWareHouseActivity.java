package cn.piesat.sanitation.ui.activity;

import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.util.UiUtils;

/**
 * 耗材新增、详情
 *
 */
public class AddWareHouseActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.etReportPerson)
    EditText etReportPerson;
    @BindView(R.id.etStation)
    EditText etStation;
    @BindView(R.id.etArea)
    EditText etArea;

    private boolean isEdit=false;//是否可编辑 用于详情页展示

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_ware_house;
    }

    @Override
    protected void initView() {
        isEdit=getIntent().getBooleanExtra("isEdit",false);
        tv_title.setText(isEdit?"新增耗材上报":"耗材上报详情");

        if (BaseApplication.getUserInfo()!=null){
            if (BaseApplication.getUserInfo().name!=null){
                etReportPerson.setText(BaseApplication.getUserInfo().name);
            }
            if (BaseApplication.getUserInfo().areaCount!=null){
                etArea.setText(BaseApplication.getUserInfo().areaCount);
            }
            if (BaseApplication.getUserInfo().deptNameCount!=null){
                etStation.setText(BaseApplication.getUserInfo().deptNameCount);
            }
        }

    }

    @Override
    protected void initData() {

    }
}
