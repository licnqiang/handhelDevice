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
    public void addAll(List<OrderList.RowsBean> data) {
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
            //???????????????????????????
            //???????????????0 -???????????????1-?????????????????????2-?????????????????????3-?????????????????????4-?????????????????????5- ?????????
            itemView.setOnClickListener(this);
            OrderList.RowsBean rowsBean = mData.get(position);
            orderNum.setText("?????????:  " + rowsBean.ydhBiztyd);
            orderSendAddress.setText(rowsBean.fscmc);
            time.setText(rowsBean.jhqysjBiztyd);
            if (rowsBean.status == 0) {             //0 -????????????
                orderState.setText("?????????");
                orderState.setTextColor(Color.parseColor("#888888"));
                orderState.setBackgroundResource(R.drawable.gay_frame);
                tv_tag.setBackgroundResource(R.mipmap.end);
            } else if (rowsBean.status == 1) {      //1-??????????????????
                orderState.setText("?????????");
                orderState.setTextColor(Color.parseColor("#E95E28"));
                orderState.setBackgroundResource(R.drawable.orange_frame);
                tv_tag.setBackgroundResource(R.mipmap.start);
            } else if (rowsBean.status == 5) {      //2-?????????
                orderState.setText("?????????");
                orderState.setTextColor(Color.parseColor("#888888"));
                orderState.setBackgroundResource(R.drawable.gay_frame);
                tv_tag.setBackgroundResource(R.mipmap.end);
            } else {
                orderState.setText("?????????");
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