package swe574.boun.edu.androidproject.adapters;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.lang.reflect.Field;

public class GroupTabLayout extends TabLayout {

    public GroupTabLayout(Context context) {
        super(context);
    }

    public GroupTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            if (getTabCount() == 0) {
                return;
            }
            Field field = TabLayout.class.getDeclaredField("mTabMinWidth");
            field.setAccessible(true);
            field.set(this, (int) (getMeasuredWidth() / (float) getTabCount()) * 1.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
