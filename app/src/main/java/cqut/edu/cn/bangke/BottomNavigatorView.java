package cqut.edu.cn.bangke;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by dhl on 2016/9/5.
 */
public class BottomNavigatorView extends LinearLayoutCompat {

    OnBottomNavigatorViewItemClickListener mOnBottomNavigatorViewItemClickListener;

    public interface OnBottomNavigatorViewItemClickListener {
        void onBottomNavigatorViewItemClick(int position, View view);
    }

    public BottomNavigatorView(Context context) {
        this(context, null);
    }

    public BottomNavigatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        inflate(context, R.layout.layout_bottom_navigator, this);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBottomNavigatorViewItemClickListener.onBottomNavigatorViewItemClick(finalI, v);
                }
            });
        }
    }

    public void select(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                selectChild(child, true);
            } else {
                selectChild(child, false);
            }
        }
    }

    private void selectChild(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                LinearLayout linearLayout = (LinearLayout) iv.getParent();
                Drawable drawable = iv.getDrawable().mutate();
                if (select) {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.nav_p_pressed));
                    drawable.setColorFilter(getResources().getColor(R.color.nav_pressed), PorterDuff.Mode.SRC_ATOP);
                } else {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.nav_p_normal));
                    drawable.setColorFilter(getResources().getColor(R.color.nav_normal), PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    public void setOnBottomNavigatorViewItemClickListener(OnBottomNavigatorViewItemClickListener listener) {
        this.mOnBottomNavigatorViewItemClickListener = listener;
    }
}
