package cn.piesat.sanitation.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.hb.dialog.myDialog.ActionSheetDialog;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.model.contract.CarStateContract;
import cn.piesat.sanitation.model.presenter.CarStatePresenter;
import cn.piesat.sanitation.ui.view.CommentItemInputModul;
import cn.piesat.sanitation.ui.view.CommentItemModul;
import cn.piesat.sanitation.util.TimeUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 事件上报
 */
public class EventReportActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        tvTitle.setText("事件上报");
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.img_back,R.id.money_report, R.id.refuse_transport,R.id.add_gaso_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.refuse_transport:     // 违章上报
                toActivity(ViolateReportActivity.class);
                break;
            case R.id.money_report://事故上报
                toActivity(AccidentReportActivity.class);
                break;
            case R.id.add_gaso_line:              //记录  加油上报
                toActivity(GasoLineReportActivity.class);  //加油列表
                break;
        }
    }

}
