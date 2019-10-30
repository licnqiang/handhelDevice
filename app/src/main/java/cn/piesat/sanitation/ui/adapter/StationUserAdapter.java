package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.MyAttendanceLBean;
import cn.piesat.sanitation.data.OrderList;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class StationUserAdapter extends RecyclerView.Adapter<StationUserAdapter.StationUserViewHolder> {
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private StationUserAdapter.OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(StationUserAdapter.OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public StationUserAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    public void refreshData(List<String> data) {
        if (null != mData) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public StationUserAdapter.StationUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StationUserAdapter.StationUserViewHolder(mInflater.inflate(R.layout.item_user_station, parent, false));
    }

    @Override
    public void onBindViewHolder(StationUserAdapter.StationUserViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class StationUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private int clickPosition;

        public StationUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(final int position) {
            clickPosition = position;

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, clickPosition);
            }
        }
    }

}
