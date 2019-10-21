package cn.piesat.sanitation.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.ui.adapter.HomeMenuAdapter;
import cn.piesat.sanitation.ui.adapter.OrderAdapter;

/**
 * 站长-订单列表
 */
public class OrderListActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    private OrderAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("订单列表");
        adapter = new OrderAdapter(this, null);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
