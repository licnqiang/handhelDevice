package cn.piesat.sanitation.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.OrderContract;
import cn.piesat.sanitation.model.presenter.OrderPresenter;
import cn.piesat.sanitation.ui.activity.CarBreakDownActivity;
import cn.piesat.sanitation.ui.activity.CarFaultActivity;
import cn.piesat.sanitation.ui.activity.CarMaintenanceActivity;
import cn.piesat.sanitation.ui.activity.CarSerViceActivity;
import cn.piesat.sanitation.ui.activity.DriverOrderDetailActivity;
import cn.piesat.sanitation.ui.activity.DriverOrderListActivity;
import cn.piesat.sanitation.ui.activity.ExpenseAccountActivity;
import cn.piesat.sanitation.ui.adapter.OrderAdapter;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 司机工作模块
 */
public class WorkDriverFragment extends BaseFragment implements OrderContract.QueryOrderList ,OrderAdapter.OnRecyclerViewItemClickListener{
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;
    private OrderAdapter adapter;

    List<OrderList.RowsBean> rowsBeans;

    OrderPresenter orderPresenter;

    private int pageNum=1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work_driver;
    }

    @Override
    protected void initView() {
        rowsBeans=new ArrayList<>();
        adapter = new OrderAdapter(getActivity(), rowsBeans);
        adapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecylerView.setAdapter(adapter);
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                orderPresenter.QueryOrderList(pageNum, SysContant.CommentTag.pageSize, BaseApplication.getUserInfo().id,null,null);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                orderPresenter.QueryOrderList(pageNum, SysContant.CommentTag.pageSize, BaseApplication.getUserInfo().id,null,null);
            }
        });
    }

    @Override
    protected void initData() {
        orderPresenter = new OrderPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoadingDialog();
        orderPresenter.QueryOrderList(pageNum, SysContant.CommentTag.pageSize, BaseApplication.getUserInfo().id,null,null);
    }
    @Override
    public void Error(String errorMsg) {
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(getActivity(), errorMsg);
    }

    @Override
    public void SuccessFinshByOrderList(OrderList orderList) {
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=orderList.rows&&orderList.rows.size()>0){

            if (pageNum==1){
                adapter.refreshData(orderList.rows);
            }else {
                adapter.addAll(orderList.rows);
            }
        }else {
            ToastUtil.show(getActivity(),"没有更多数据咯!");
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        OrderList.RowsBean rowsBean= rowsBeans.get(position);
        startActivity(new Intent(getActivity(), DriverOrderDetailActivity.class).putExtra(SysContant.CommentTag.comment_key,rowsBean));
    }

}
