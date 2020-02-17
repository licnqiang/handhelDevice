package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.AccidentReportBean;

public class WareHouseAdapter extends BaseAdapter {

    private Context context;

    public WareHouseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViolateViewHolder holder=null;
        if (convertView==null){
            holder=new ViolateViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_violate_report,null);
            holder.tvCarNumber=convertView.findViewById(R.id.tvCarNumber);
            holder.tvPerson=convertView.findViewById(R.id.tvPerson);
            holder.tvDate=convertView.findViewById(R.id.tvDate);
            holder.tvStation=convertView.findViewById(R.id.tvStation);

            convertView.setTag(holder);
        }else {
            holder= (ViolateViewHolder) convertView.getTag();
        }

        holder.tvCarNumber.setText("耗材：防护服");
        holder.tvPerson.setText("上报人：李恩");
        holder.tvDate.setText("入库时间：2019-02-20 12：30");
        holder.tvStation.setText("类型：一般工具  数量：1000 件");


        return convertView;
    }


    class ViolateViewHolder{

        TextView tvPerson,tvDate,tvCarNumber,tvStation;
//        ImageView imgDelete;
    }

}
