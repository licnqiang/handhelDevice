package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.UserListBean;
import cn.piesat.sanitation.ui.activity.MyAttendanceListActivity;
import cn.piesat.sanitation.util.UiUtils;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserListBean.RowsBean> userListBean;


    public UserListAdapter(Context context, ArrayList<UserListBean.RowsBean> userBean) {
        this.context = context;
        this.userListBean = userBean;
    }

    @Override
    public int getCount() {
        return userListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return userListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserListViewHolder holder = null;

        if (convertView == null) {
            holder = new UserListViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_station, null);
            holder.tvHead = convertView.findViewById(R.id.tvHead);
            holder.user_name = convertView.findViewById(R.id.user_name);
            holder.user_type = convertView.findViewById(R.id.user_type);

            convertView.setTag(holder);
        } else {
            holder = (UserListViewHolder) convertView.getTag();
        }

        UserListBean.RowsBean userBean = userListBean.get(position);

        holder.user_name.setText(TextUtils.isEmpty(userBean.name) ? "无" : "" + userBean.name);
        holder.tvHead.setText(UiUtils.getPinYinHeadChar(userBean.name.substring(0, 1)));

        int type = Integer.parseInt(userBean.userType);
        switch (type) {
            case 1:
                holder.user_type.setText("管理员");
                break;
            case 2:
                holder.user_type.setText("普通用户");
                break;
            case 3:
                holder.user_type.setText("环卫集团员工");
                break;
            case 4:
                holder.user_type.setText("站长");
                break;
            case 5:
                holder.user_type.setText("操作工");
                break;
            case 6:
                holder.user_type.setText("扫保人员");
                break;
            case 7:
                holder.user_type.setText("司机");
                break;
        }

        convertView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, MyAttendanceListActivity.class)
                    .putExtra("userId", userBean.id)
                    .putExtra("userName", userBean.name)
            );
        });


        return convertView;
    }

    class UserListViewHolder {
        TextView tvHead, user_name, user_type;
    }
}
