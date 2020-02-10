package cn.piesat.sanitation.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.piesat.sanitation.R;

/**
 * @author lq
 * @fileName 工作模块公用模块(新样式 圆形)
 * @data on  2019/10/21 18:15
 * @describe TODO
 */
public class ItemWorkModul extends LinearLayout {
    ImageView itemIcon;
    TextView itemTitle;
    LinearLayout rlItem;

    public ItemWorkModul(Context context) {
        super(context);
    }


    public ItemWorkModul(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemWorkModul(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.item_working_modul_new, this, true);
        itemIcon = findViewById(R.id.item_icon);
        itemTitle = findViewById(R.id.item_title);
        rlItem = findViewById(R.id.rl_item);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WorkModulNew,
                defStyle, 0);
        int icon = a.getResourceId(R.styleable.WorkModulNew_new_work_modul_icon,
                0);
        String title = a.getString(R.styleable.WorkModulNew_new_work_modul_title);
        itemIcon.setImageResource(icon);
        itemTitle.setText(title);

        a.recycle();
    }


}
