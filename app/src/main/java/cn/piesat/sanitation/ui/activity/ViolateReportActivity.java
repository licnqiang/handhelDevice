package cn.piesat.sanitation.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
import cn.piesat.sanitation.data.ViolateReportBean;
import cn.piesat.sanitation.model.contract.ReportContract;
import cn.piesat.sanitation.model.presenter.ReportPresenter;
import cn.piesat.sanitation.ui.adapter.ViolateReportAdapter;
import cn.piesat.sanitation.util.DialogUtils;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长  违章上报
 * Created by sen.luo on 2020/1/2.
 */
public class ViolateReportActivity extends BaseActivity implements ReportContract.getViolateReportIView {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.lvViolate)
    ListView lvViolate;
    @BindView(R.id.springView)
    SpringView springView;

    private ReportPresenter reportPresenter;
    private int pageNumber=1;

    private List<ViolateReportBean.ViolateListBean>violateListBeans;
    private ViolateReportAdapter violateReportAdapter;
    private int deletePoistion=-1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_violate_report;
    }

    @Override
    protected void initView() {
        tv_title.setText("违章上报");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(3!= BaseApplication.getUserInfo().userType?View.VISIBLE:View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> startActivityForResult(new Intent(this,AddViolateReportActivity.class).putExtra("isEdit",true),0));


        reportPresenter=new ReportPresenter(this);
        violateListBeans=new ArrayList<>();
        violateReportAdapter=new ViolateReportAdapter(this,violateListBeans);
        lvViolate.setAdapter(violateReportAdapter);

        violateReportAdapter.setReportOperateMore(new ViolateReportAdapter.ReportOperateMore() {
            @Override
            public void getReportDelete(int position, String id) {

                DialogUtils.generalDialog(ViolateReportActivity.this, "确定删除当前条目吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(violateListBeans.get(position).id)){
                            return ;
                        }
                        deletePoistion=position;
                        Map<String,String>map =new HashMap<>();
                        map.put("id",violateListBeans.get(position).id);
                        showLoadingDialog();
                        reportPresenter.getViolateReportDelete(map);
                    }
                });
            }
        });


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

        lvViolate.setOnItemClickListener((parent, view, position, id) ->
                        startActivity(new Intent(this,AddViolateReportActivity.class)
                                .putExtra("isEdit",false)
                        .putExtra("report_id",violateListBeans.get(position).id))
                );

    }

    @Override
    protected void initData() {

        Map<String,String> map =new HashMap<>();
        map.put("curren", String.valueOf(pageNumber));
        map.put("size", SysContant.CommentTag.pageSize);
        showLoadingDialog();
        reportPresenter.getViolateReportList(map);
    }

    @Override
    public void SuccessOnReportList(ViolateReportBean bean) {
        dismiss();
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        if (bean.records!=null&&bean.records.size()>0){
            if (pageNumber==1){
                violateListBeans.clear();
            }
            violateListBeans.addAll(bean.records);
            violateReportAdapter.notifyDataSetChanged();

        }else {
            ToastUtil.show(this,"没有更多数据!");
        }

    }

    @Override
    public void Error(String msg) {
        ToastUtil.show(ViolateReportActivity.this,msg);
        dismiss();
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
    }



    @Override
    public void SuccessOnReportDelete(String msg) {
        ToastUtil.show(ViolateReportActivity.this,msg);
        dismiss();
        if (violateListBeans!=null){
            violateListBeans.remove(deletePoistion);
            violateReportAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0){
            pageNumber=1;
            initData();
        }
    }
}
