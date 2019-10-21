package cn.piesat.sanitation.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.piesat.sanitation.R;

/**
 * @author lq
 * @fileName 工作模块公用模块
 * @data on  2019/10/21 18:15
 * @describe TODO
 */
public class MyWorkModul extends LinearLayout {
    ImageView itemIcon;
    TextView itemTitle;
    RelativeLayout rlItem;

    public MyWorkModul(Context context) {
        super(context);
    }


    public MyWorkModul(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWorkModul(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.item_working_modul, this, true);
        itemIcon = findViewById(R.id.item_icon);
        itemTitle = findViewById(R.id.item_title);
        rlItem = findViewById(R.id.rl_item);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WorkModul,
                defStyle, 0);
        int icon = a.getResourceId(R.styleable.WorkModul_work_modul_icon,
                0);
        String title = a.getString(R.styleable.WorkModul_work_modul_title);
        itemIcon.setImageResource(icon);
        itemTitle.setText(title);

        a.recycle();
    }


}
