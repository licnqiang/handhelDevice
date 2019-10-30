package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import cn.piesat.sanitation.R;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.StationUserViewHolder> {
    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private EventListAdapter.OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(EventListAdapter.OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public EventListAdapter(Context mContext, List<String> data) {
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
    public EventListAdapter.StationUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventListAdapter.StationUserViewHolder(mInflater.inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(EventListAdapter.StationUserViewHolder holder, int position) {
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
