package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.OrderList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.SelectedPicViewHolder> {
    private Context mContext;
    private List<OrderList.RowsBean> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public OrderAdapter(Context mContext, List<OrderList.RowsBean> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    public void refreshData(List<OrderList.RowsBean> data) {
        if (null != mData) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.item_order, parent, false));
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
            OrderList.RowsBean rowsBean = mData.get(position);
            orderNum.setText("订单号:" + rowsBean.ydhBiztyd);
            orderSendAddress.setText(rowsBean.fscmc);
            time.setText("执行时间:" + rowsBean.jhqysjBiztyd);
            if (rowsBean.status == 0) {             //0 -指派取消
                orderState.setText("已取消");
                orderState.setBackgroundResource(R.drawable.block_frame);
                tv_tag.setBackgroundResource(R.drawable.block_frame);
            } else if (rowsBean.status == 1) {      //1-已指派未接单
                orderState.setText("未接单");
                orderState.setBackgroundResource(R.drawable.orange_frame);
                tv_tag.setBackgroundResource(R.drawable.orange_frame);
            } else if (rowsBean.status == 5) {      //2-已完成
                orderState.setText("已完成");
                orderState.setBackgroundResource(R.drawable.green_frame);
                tv_tag.setBackgroundResource(R.drawable.green_frame);
            } else {
                orderState.setText("进行中");
                orderState.setBackgroundResource(R.drawable.blue_frame);
                tv_tag.setBackgroundResource(R.drawable.blue_frame);
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