package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.model.contract.MaintainReportContract;
import cn.piesat.sanitation.model.presenter.MaintainReportPresenter;
import cn.piesat.sanitation.ui.adapter.MaintainOrderAdapter;
import cn.piesat.sanitation.ui.adapter.TabPagerAdapter;
import cn.piesat.sanitation.ui.fragment.FragmentMaintainIs;
import cn.piesat.sanitation.ui.fragment.FragmentMaintainNo;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepIs;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepNo;
import cn.piesat.sanitation.util.RoleIdSortUtil;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 维修审批
 */
public class MaintainActivity extends BaseActivity implements MaintainOrderAdapter.OnRecyclerViewItemClickListener, MaintainReportContract.getMaintainReportSIView {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;

    private MaintainOrderAdapter adapter;
    List<MaintainList.RecordsBean> rowsBeans;
    MaintainReportPresenter maintainReportPresenter;
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintain;
    }

    @Override
    protected void initView() {
        tv_title.setText("维修审批");
        maintainReportPresenter = new MaintainReportPresenter(this);
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.icon_add));
        iv_right.setVisibility(3 != BaseApplication.getUserInfo().userType ? View.VISIBLE : View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> toActivity(AddMaintainReportActivity.class));

        rowsBeans = new ArrayList<>();
        adapter = new MaintainOrderAdapter(this, rowsBeans);
        adapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(adapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                initData();
            }
        });

    }

    @Override
    protected void initData() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", String.valueOf(pageNum));
        hashMap.put("size", "20"); //每页显示20条

        if(BaseApplication.getUserInfo().userType!=3){
            hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //站点名称
        }
        hashMap.put("roleId", RoleIdSortUtil.getMinRoleId(BaseApplication.getIns().getUserRoleIdList())); //角色id 取最小角色
        hashMap.put("userId", SpHelper.getStringValue(SysContant.userInfo.USER_ID)); //用户id

        showLoadingDialog("加载中", false);
        maintainReportPresenter.getMaintainReportList(hashMap);
    }



    @Override
    public void onItemClick(View view, int position) {
        MaintainList.RecordsBean rowsBean = rowsBeans.get(position);
        startActivity(new Intent(this, MaintainReportDetailActivity.class).putExtra(SysContant.CommentTag.comment_key, rowsBean));
    }


    @Override
    public void SuccessOnReportList(MaintainList maintainList) {
        if (null != springView) {
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null != maintainList.records && maintainList.records.size() > 0) {

            if (pageNum == 1) {
                adapter.refreshData(maintainList.records);
            } else {
                adapter.addAll(maintainList.records);
            }
        } else {
            ToastUtil.show(this, "没有更多数据咯!");
        }
    }

    @Override
    public void Error(String msg) {
        if (null != springView) {
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this, msg);
    }

}
