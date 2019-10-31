package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarBreakDown;
import cn.piesat.sanitation.model.contract.CarMaintenanceContract;
import cn.piesat.sanitation.model.presenter.CarMaintenancePresenter;
import cn.piesat.sanitation.ui.adapter.CarBreakDownAdapter;
import cn.piesat.sanitation.ui.adapter.CarMaintenanceAdapter;
import cn.piesat.sanitation.util.ToastUtil;

public class CarBreakDownActivity extends BaseActivity implements CarMaintenanceContract.CarBreakDownView, CarBreakDownAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;

    CarMaintenancePresenter carMaintenancePresenter;
    CarBreakDownAdapter carBreakDownAdapter;
    private ArrayList<CarBreakDown.RowsBean> rowsBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_break_down;
    }

    @Override
    protected void initView() {
        tvTitle.setText("故障信息");
        setCompressStationAdapter();
        showLoadingDialog();
        carMaintenancePresenter = new CarMaintenancePresenter(this);
    }


    /**
     * 人员列表
     */
    private void setCompressStationAdapter() {
        rowsBeanList = new ArrayList<>();
        carBreakDownAdapter = new CarBreakDownAdapter(this, rowsBeanList);
        carBreakDownAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(carBreakDownAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        carMaintenancePresenter.QueryCarBreakDown(0, -1);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
            startActivity(new Intent(this,CarFaultDetailShowActivity.class).putExtra(SysContant.CommentTag.comment_key,rowsBeanList.get(position)));
    }

    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinsh(CarBreakDown carBreakDown) {
        dismiss();
        carBreakDownAdapter.refreshData(carBreakDown.rows);
    }



}
