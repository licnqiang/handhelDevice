package cn.piesat.sanitation.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.ApprovalStateBean;
import cn.piesat.sanitation.data.MaintainList;


/**
 * Created by User on 2018/9/2.
 */

public class ApprovalDialog {
    private Context context;
    private clickListener mClickListener;
    private Dialog mTaskDialogWindow;

    public ApprovalDialog(Context context) {
        this.context = context;
    }

    public void setClickListener(clickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public interface clickListener {
        void receiveClick();

        void refuseClick();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (mTaskDialogWindow.isShowing()) {
            mTaskDialogWindow.dismiss();
        }
    }

    public void showTaskDialog(List<ApprovalStateBean> approvalStates,String approvalstatus,String appContent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.approval_dialog, null);
        mTaskDialogWindow = new Dialog(context, R.style.Dialog);
        mTaskDialogWindow.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        WindowManager.LayoutParams params = mTaskDialogWindow.getWindow().getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        params.height = (int) (d.heightPixels * 0.2); // 高度设置为屏幕的0.6
        mTaskDialogWindow.getWindow().setAttributes(params);

        ImageView imageView = view.findViewById(R.id.iv_colse);
        TextView tvInfo = view.findViewById(R.id.tv_info);

        /**
         * 显示驳回信息
         */
        if(approvalstatus.equals("02")){   //审核状态 01 审核中  02 驳回 03 审核完成了
            if(null!=appContent&&!appContent.isEmpty()){
                tvInfo.setText("驳回理由："+appContent);
            }
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskDialogWindow.dismiss();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        //在此处修改布局排列方向
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        ListAdapter listAdapter = new ListAdapter(approvalStates,approvalstatus);
        recyclerView.setAdapter(listAdapter);


        mTaskDialogWindow.show();
    }


}
