package cn.berfy.framework.support.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import cn.berfy.framework.R;
import cn.berfy.framework.utils.ViewUtils;

/**
 * Created by Berfy on 2017/8/15.
 * 状态栏间隔布局
 */

public class StatusMarginBar extends LinearLayout {

    public StatusMarginBar(Context context) {
        super(context);
    }

    public StatusMarginBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusMarginBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        init();
    }

    private void init() {
        try {
            setBackgroundResource(R.color.translate_titlebar);
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                layoutParams.height = ViewUtils.getStatusBarHeight(getContext());
                setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
