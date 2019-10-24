package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.OrderContract;
import cn.piesat.sanitation.model.presenter.OrderPresenter;
import cn.piesat.sanitation.ui.adapter.HomeMenuAdapter;
import cn.piesat.sanitation.ui.adapter.OrderAdapter;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长-订单列表
 */
public class OrderListActivity extends BaseActivity implements OrderContract.QueryOrderList, OrderAdapter.OnRecyclerViewItemClickListener {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    private OrderAdapter adapter;

    List<OrderList.RowsBean> rowsBeans;

    OrderPresenter orderPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("订单列表");
        rowsBeans = new ArrayList<>();
        adapter = new OrderAdapter(this, rowsBeans);
        adapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        orderPresenter = new OrderPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog("加载中", false);
        orderPresenter.QueryOrderList(1, -1, null, null, null);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinshByOrderList(OrderList orderList) {
        dismiss();
        adapter.refreshData(orderList.rows);
    }

    @Override
    public void onItemClick(View view, int position) {
        OrderList.RowsBean rowsBean = rowsBeans.get(position);
        startActivity(new Intent(this, HeaderOrderDetailActivity.class).putExtra(SysContant.CommentTag.comment_key, rowsBean));
    }
}
