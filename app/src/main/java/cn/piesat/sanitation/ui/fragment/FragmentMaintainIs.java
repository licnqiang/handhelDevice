package cn.piesat.sanitation.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.data.UpKeepList;
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.model.contract.MaintainReportContract;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.model.presenter.GasonLineReportPresenter;
import cn.piesat.sanitation.model.presenter.MaintainReportPresenter;
import cn.piesat.sanitation.model.presenter.UpkeepReportPresenter;
import cn.piesat.sanitation.ui.activity.MaintainReportDetailActivity;
import cn.piesat.sanitation.ui.activity.UpKeepReportDetailActivity;
import cn.piesat.sanitation.ui.adapter.GasonLineOrderAdapter;
import cn.piesat.sanitation.ui.adapter.MaintainOrderAdapter;
import cn.piesat.sanitation.ui.adapter.UpKeepOrderAdapter;
import cn.piesat.sanitation.util.ToastUtil;

public class FragmentMaintainIs extends BaseFragment implements MaintainOrderAdapter.OnRecyclerViewItemClickListener,MaintainReportContract.getMaintainIsReportSIView{
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;

    private MaintainOrderAdapter adapter;
    List<MaintainList.RecordsBean> rowsBeans;
    MaintainReportPresenter maintainReportPresenter;
    private int pageNum=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep_list;
    }

    @Override
    protected void initView() {
        rowsBeans = new ArrayList<>();
        adapter = new MaintainOrderAdapter(getActivity(), rowsBeans);
        adapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecylerView.setAdapter(adapter);
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                maintainReportPresenter.getMaintainIsReportList(pageNum);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                maintainReportPresenter.getMaintainIsReportList(pageNum);
            }
        });

    }

    @Override
    protected void initData() {
        maintainReportPresenter=new MaintainReportPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoadingDialog("加载中", false);
        pageNum=1;
        maintainReportPresenter.getMaintainIsReportList(pageNum);
    }

    @Override
    public void onItemClick(View view, int position) {
        MaintainList.RecordsBean rowsBean = rowsBeans.get(position);
        startActivity(new Intent(getActivity(), MaintainReportDetailActivity.class).putExtra(SysContant.CommentTag.comment_key, rowsBean));
    }

    @Override
    public void SuccessOnReportList(MaintainList maintainList) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=maintainList.records&&maintainList.records.size()>0){

            if (pageNum==1){
                adapter.refreshData(maintainList.records);
            }else {
                adapter.addAll(maintainList.records);
            }
        }else {
            ToastUtil.show(getActivity(),"没有更多数据咯!");
        }
    }

    @Override
    public void Error(String msg) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(getActivity(), msg);
    }
}
