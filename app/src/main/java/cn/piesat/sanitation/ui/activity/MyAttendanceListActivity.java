package cn.piesat.sanitation.ui.activity;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fantasy.doubledatepicker.DoubleDateSelectDialog;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.MyAttendanceLBean;
import cn.piesat.sanitation.data.UserListBean;
import cn.piesat.sanitation.model.contract.MineContract;
import cn.piesat.sanitation.model.presenter.MinePresenter;
import cn.piesat.sanitation.ui.adapter.AttendanceAdapter;
import cn.piesat.sanitation.util.DateUtils;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 考勤列表
 * Created by sen.luo on 2019/10/24.
 */

public class MyAttendanceListActivity extends BaseActivity implements MineContract.MyAttendanceListView{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.lvAttendance)
    ListView lvAttendance;
    @BindView(R.id.tvPrompt)
    TextView tvPrompt;


    private String userId="";
    private MinePresenter minePresenter;
    private ArrayList<MyAttendanceLBean.DataBean> attendanceList;
    private AttendanceAdapter attendanceAdapter;
    private String startDate= DateUtils.getMonthAgo(new Date(System.currentTimeMillis()));
    private String endDate= new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_attendance_list;
    }

    @Override
    protected void initView() {
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        minePresenter=new MinePresenter(this);
        if (TextUtils.isEmpty(getIntent().getStringExtra("userId"))){
            tv_title.setText("我的考勤");
            userId=BaseApplication.getUserInfo().id;
        }else {
            userId=getIntent().getStringExtra("userId");
            tv_title.setText(getIntent().getStringExtra("userName")+"的考勤");
        }

        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_calendar));
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(v -> {
            DoubleDateSelectDialog doubleDateSelectDialog =new DoubleDateSelectDialog(this);
            doubleDateSelectDialog.setOnDateSelectFinished(new DoubleDateSelectDialog.OnDateSelectFinished() {
                @Override
                public void onSelectFinished(String s, String s1) {
                    LogUtil.e("s:"+s,"/s1:"+s1);
                    startDate=s;
                    endDate=s1;
                    pageNum=1;
                    initData();
                }
            });
            doubleDateSelectDialog.show();
        });

        attendanceList=new ArrayList<>();
        attendanceAdapter=new AttendanceAdapter(this,attendanceList);
        lvAttendance.setAdapter(attendanceAdapter);

        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                initData();
            }
        });
    }


    private int pageNum=1;
    @Override
    protected void initData() {
        tvPrompt.setText("当前显示 "+startDate+" 到 "+endDate+" 的考勤记录");
        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("userId", userId);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageNum", String.valueOf(pageNum));
        map.put("pageSize", SysContant.CommentTag.pageSize);
        minePresenter.getMyAttendanceList(map);
    }

    @Override
    public void Error(String msg) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this,msg);
    }

    @Override
    public void SuccessOnAttendanceList(MyAttendanceLBean myAttendanceLBean) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
            dismiss();
            if (null!=myAttendanceLBean.list&&myAttendanceLBean.list.size()>0){

                if (pageNum==1){
                    attendanceList.clear();
                }
                attendanceList.addAll(myAttendanceLBean.list);
                attendanceAdapter.notifyDataSetChanged();
            }else {
                ToastUtil.show(this,"没有更多数据咯!");
            }
    }


}
