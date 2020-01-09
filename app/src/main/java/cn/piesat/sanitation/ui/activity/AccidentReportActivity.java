package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import cn.piesat.sanitation.data.AccidentReportBean;
import cn.piesat.sanitation.model.contract.AccidentReportContract;
import cn.piesat.sanitation.model.presenter.AccidentReportPresenter;
import cn.piesat.sanitation.ui.adapter.AccidetReportAdapter;
import cn.piesat.sanitation.util.ToastUtil;


/**
 * 站长事故上报列表
 *  Created by sen.luo on 2020/1/3.
 */
public class AccidentReportActivity extends BaseActivity implements AccidentReportContract.GetAccidentReportIView {
    @BindView(R.id.lvAccident)
    ListView lvAccident;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    private List<AccidentReportBean.AccidentListBean> accidentListBeans;

    private AccidentReportPresenter accidentReportPresenter;
    private AccidetReportAdapter accidetReportAdapter;
    private int pageNumber=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accident_report;
    }

    @Override
    protected void initView() {
        tv_title.setText("事故上报");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(3!= BaseApplication.getUserInfo().userType?View.VISIBLE:View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> startActivity(new Intent(this,AddAccidentReportActivity.class).putExtra("isEdit",true)));


        accidentReportPresenter=new AccidentReportPresenter(this);

        accidentListBeans=new ArrayList<>();
        accidetReportAdapter=new AccidetReportAdapter(this,accidentListBeans);
        lvAccident.setAdapter(accidetReportAdapter);

       /* accidetReportAdapter.setReportOperateMore(new AccidetReportAdapter.ReportOperateMore() {
            @Override
            public void getReportDelete(int position, String id) {

            }
        });*/

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                initData();
            }
        });


        lvAccident.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(this,AddAccidentReportActivity.class)
                        .putExtra("isEdit",false)
                        .putExtra("report_id",accidentListBeans.get(position).id))
        );
    }

    @Override
    protected void initData() {
        Map<String,String> map =new HashMap<>();
        map.put("curren", String.valueOf(pageNumber));
        map.put("size", SysContant.CommentTag.pageSize);
        showLoadingDialog();
        accidentReportPresenter.getAccidentReportList(map);
    }

    @Override
    public void successOnAccidentList(AccidentReportBean bean) {
        dismiss();
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        if (bean.records!=null&&bean.records.size()>0){
            if (pageNumber==1){
                accidentListBeans.clear();
            }
            accidentListBeans.addAll(bean.records);
            accidetReportAdapter.notifyDataSetChanged();

        }else {
            ToastUtil.show(this,"没有更多数据!");
        }

    }

    @Override
    public void error(String msg) {
        ToastUtil.show(AccidentReportActivity.this,msg);
        dismiss();
    }

    @Override
    public void successOnAccidentDelete(String msg) {

    }
}
