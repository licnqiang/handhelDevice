package cn.piesat.sanitation.ui.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.ApprovalStateBean;
import cn.piesat.sanitation.data.MaintainList;

/**
 * @author lq
 * @fileName ListAdapter
 * @data on  2020/1/13 15:22
 * @describe TODO
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ApprovalStateBean> mDataList;
    private String approvalstatuss;

    public ListAdapter(List<ApprovalStateBean> listDatas,String approvalstatus) {
        mDataList = listDatas;
        approvalstatuss = approvalstatus;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approval_item,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApprovalStateBean data = mDataList.get(position);
        if (position == mDataList.size() - 1) {
            holder.tvXian.setVisibility(View.GONE);   //最后一位 隐藏横线
        }

        //判断该节点是否审核 显示图标
        if(data.TT==1){    //1 为 已审核 2为未审核
            holder.ivImage.setImageResource(R.mipmap.pass_person);
        }else {
            holder.ivImage.setImageResource(R.mipmap.norm_person);
        }

        /**
         * 判断详情是否审批驳回，显示红色图标
         */
        if(approvalstatuss.equals("02")){   //审核状态 01 审核中  02 驳回 03 审核完成了
            //判断list下条数据如果是未审批就是 目前这个角色驳回的
            if(mDataList.size()-1<=position){
                holder.ivImage.setImageResource(R.mipmap.no_person);
            }else {
                ApprovalStateBean approvalStateBean = mDataList.get(position + 1);
                if (approvalStateBean.TT == 2) {
                    holder.ivImage.setImageResource(R.mipmap.no_person);
                }
            }
        }

        holder.tvName.setText(data.NODE_NAME);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvXian;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_1);
            tvName = (TextView) itemView.findViewById(R.id.tv_1);
            tvXian = (TextView) itemView.findViewById(R.id.tv_xian);

        }
    }
}
