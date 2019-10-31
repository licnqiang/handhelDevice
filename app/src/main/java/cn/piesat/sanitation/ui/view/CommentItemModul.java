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
import cn.piesat.sanitation.common.BaseActivity;

/**
 * @author lq
 * @fileName 工作模块公用模块
 * @data on  2019/10/21 18:15
 * @describe TODO
 */
public class CommentItemModul extends LinearLayout {
    TextView itemTitle;
    TextView itemValue;
    ImageView ivNext;

    public CommentItemModul(Context context) {
        super(context);
    }


    public CommentItemModul(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentItemModul(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(getContext()).inflate(R.layout.comment_item_modul, this, true);
        itemValue = findViewById(R.id.comment_item_value);
        itemTitle = findViewById(R.id.comment_item_name);
        ivNext = findViewById(R.id.iv_next);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommentItemModul,
                defStyle, 0);
        String title = a.getString(R.styleable.CommentItemModul_comment_item_title);
        String value = a.getString(R.styleable.CommentItemModul_comment_item_value);
        boolean visibility = a.getBoolean(R.styleable.CommentItemModul_comment_item_next_visibility,true);

        ivNext.setVisibility(visibility?VISIBLE:INVISIBLE);
        itemTitle.setText(title);
        itemValue.setText(value);
        a.recycle();
    }


    public void setText(String msg) {
        itemValue.setText(msg);
    }

    public String getText() {
        return itemValue.getText()+"";
    }


}
