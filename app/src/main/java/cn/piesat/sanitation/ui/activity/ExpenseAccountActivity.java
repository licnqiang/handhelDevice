package cn.piesat.sanitation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;

/**
 * 费用报销
 */
public class ExpenseAccountActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expense_account;
    }

    @Override
    protected void initView() {
        tvTitle.setText("费用报销");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
