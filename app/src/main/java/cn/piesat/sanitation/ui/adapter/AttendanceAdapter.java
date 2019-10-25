package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.MyAttendanceLBean;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class AttendanceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MyAttendanceLBean.DataBean> attendanceList;

    public AttendanceAdapter(Context context, ArrayList<MyAttendanceLBean.DataBean> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @Override
    public int getCount() {
        return attendanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AttendanceViewHolder holder;

        if (convertView==null){
            holder=new AttendanceViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_my_attendance,null);
            holder.tvDate=convertView.findViewById(R.id.tvDate);
            holder.tvGoWorkTime=convertView.findViewById(R.id.tvGoWorkTime);
            holder.tvOffWorkTime=convertView.findViewById(R.id.tvOffWorkTime);

            convertView.setTag(holder);
        }else {
            holder= (AttendanceViewHolder) convertView.getTag();
        }


        MyAttendanceLBean.DataBean dataBean=attendanceList.get(0);

        holder.tvDate.setText("日期："+dataBean.createDate);
        holder.tvGoWorkTime.setText("上班打卡："+ (TextUtils.isEmpty(dataBean.startTime)? "缺卡":dataBean.startTime));
        holder.tvOffWorkTime.setText("下班打卡："+ (TextUtils.isEmpty(dataBean.endTime)? "缺卡":dataBean.endTime));

        return convertView;
    }

    class AttendanceViewHolder{
        TextView tvDate,tvGoWorkTime,tvOffWorkTime;
    }
}
