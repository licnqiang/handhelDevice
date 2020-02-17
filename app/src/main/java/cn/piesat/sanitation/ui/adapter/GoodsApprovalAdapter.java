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

public class GoodsApprovalAdapter extends BaseAdapter {

    private Context context;

    public GoodsApprovalAdapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_gason_line,null);
            holder.tvType=convertView.findViewById(R.id.order_state);
            holder.tvPerson=convertView.findViewById(R.id.order_send_address);
            holder.tvDate=convertView.findViewById(R.id.time);
            holder.tvNumber=convertView.findViewById(R.id.order_num);

            holder.tvDate.setText("2020-02-17 12:45");
            holder.tvPerson.setText("防护服领用");
            holder.tvType.setText("审批中");
            holder.tvNumber.setText("申请人：李恩");


            convertView.setTag(holder);
        }else {
            holder= (ViolateViewHolder) convertView.getTag();
        }



        return convertView;
    }


    class ViolateViewHolder{

        TextView tvPerson,tvDate,tvType,tvNumber;
//        ImageView imgDelete;
    }

}
