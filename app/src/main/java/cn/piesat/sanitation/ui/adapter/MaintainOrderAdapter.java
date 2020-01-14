package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.data.UpKeepList;


public class MaintainOrderAdapter extends RecyclerView.Adapter<MaintainOrderAdapter.SelectedPicViewHolder> {
    private Context mContext;
    private List<MaintainList.RecordsBean> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public MaintainOrderAdapter(Context mContext, List<MaintainList.RecordsBean> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    public void refreshData(List<MaintainList.RecordsBean> data) {
        if (null != mData) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addAll(List<MaintainList.RecordsBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.item_gason_line, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.order_state)
        TextView orderState;
        @BindView(R.id.order_send_address)
        TextView orderSendAddress;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.tv_tag)
        TextView tv_tag;

        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position) {
            clickPosition = position;
            //设置条目的点击事件
            //运单状态：0 -指派取消、1-已指派未接单、2-已接单未起运、3-已起运未过磅、4-已过磅未确认、5- 已完成
            itemView.setOnClickListener(this);
            MaintainList.RecordsBean rowsBean = mData.get(position);
            orderNum.setText("订单号:  " + rowsBean.id);
            orderSendAddress.setText(rowsBean.administrativeArea);
            time.setText(rowsBean.createtime);

            if (rowsBean.approvalstatus.equals("01")) {             //01 -审核中
                orderState.setText("审核中");
                orderState.setTextColor(Color.parseColor("#2A83E9"));
                orderState.setBackgroundResource(R.drawable.blue_frame);
                tv_tag.setBackgroundResource(R.mipmap.ing);
            } else if (rowsBean.approvalstatus.equals("02")) {      //02-驳回
                orderState.setText("已驳回");
                orderState.setTextColor(Color.parseColor("#888888"));
                orderState.setVisibility(View.GONE);
                tv_tag.setVisibility(View.GONE);
            } else if (rowsBean.approvalstatus.equals("03")) {      //03-审核完成了
                orderState.setText("已完成");
                orderState.setTextColor(Color.parseColor("#888888"));
                orderState.setBackgroundResource(R.drawable.gay_frame);
                tv_tag.setBackgroundResource(R.mipmap.end);
            } else {
                orderState.setText("审核中");
                orderState.setTextColor(Color.parseColor("#2A83E9"));
                orderState.setBackgroundResource(R.drawable.blue_frame);
                tv_tag.setBackgroundResource(R.mipmap.ing);
            }

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, clickPosition);
            }
        }
    }
}