package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

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

/**
 * 故障记录界面
 */
public class CarBreakDownActivity extends BaseActivity implements CarMaintenanceContract.CarBreakDownView, CarBreakDownAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;

    CarMaintenancePresenter carMaintenancePresenter;
    CarBreakDownAdapter carBreakDownAdapter;
    private ArrayList<CarBreakDown.RowsBean> rowsBeanList;

    private int pageNum = 1;

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
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                carMaintenancePresenter.QueryCarBreakDown(pageNum, SysContant.CommentTag.pageSize);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                carMaintenancePresenter.QueryCarBreakDown(pageNum, SysContant.CommentTag.pageSize);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        carMaintenancePresenter.QueryCarBreakDown(pageNum, SysContant.CommentTag.pageSize);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(this, CarFaultDetailShowActivity.class).putExtra(SysContant.CommentTag.comment_key, rowsBeanList.get(position)));
    }

    @Override
    public void Error(String errorMsg) {
        if (null!=springView) {
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinsh(CarBreakDown carBreakDown) {
        if (null!=springView) {
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null != carBreakDown.rows && carBreakDown.rows.size() > 0) {

            if (pageNum == 1) {
                carBreakDownAdapter.refreshData(carBreakDown.rows);
            } else {
                carBreakDownAdapter.addAll(carBreakDown.rows);
            }

        } else {
            ToastUtil.show(this, "没有更多数据咯!");
        }
    }


}
