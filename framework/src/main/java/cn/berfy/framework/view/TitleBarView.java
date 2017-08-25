package cn.berfy.framework.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.berfy.framework.R;
import cn.berfy.framework.base.BaseActivity;
import cn.berfy.framework.utils.ViewUtils;

public final class TitleBarView extends RelativeLayout implements
        OnClickListener {

    // 标题文本控件
    public TextView mTitleTextView;
    // 标题左边文字
    public TextView mLeftTextView;
    // 左右侧按钮及右侧旁边按钮
    public ImageView mLeftButton, mRightButton, mSubjoinButton;
    // 右边文字
    public TextView mRightTxtView, mRightSubTextView;
    private LinearLayout mLlSecondTitle;
    private TextView mTvSecondTitle;

    public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(getContext());
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(getContext());
    }

    public TitleBarView(Context context) {
        super(context);
        initView(getContext());
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.common_titlebar, this);
        // 标题
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);

        // 左边控件
        mLeftButton = (ImageView) findViewById(R.id.title_left_button);
        mLeftTextView = (TextView) findViewById(R.id.title_left_txt);

        mTvSecondTitle = (TextView) findViewById(R.id.tv_second_title);
        mLlSecondTitle = (LinearLayout) findViewById(R.id.layout_second_title);

        // 右边按钮
        mRightButton = (ImageView) findViewById(R.id.title_right_button);
        mSubjoinButton = (ImageView) findViewById(R.id.title_subjoin_button);
        // 右边文字
        mRightTxtView = (TextView) findViewById(R.id.title_righttxt);
        mRightSubTextView = (TextView) findViewById(R.id.title_righttxt_sub);

        setLeftBtnDrawable(R.mipmap.back);
    }

    public void setTitleBgColor(int color) {
        findViewById(R.id.bg).setBackgroundColor(getResources().getColor(color));
    }

    /**
     * 设置文字颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        mTitleTextView.setTextColor(color);
    }

    /**
     * 设置标题文字
     *
     * @param text
     */
    public void setTitleText(CharSequence text) {
        mTitleTextView.setText(text);
    }

    public void setLeftText(int text) {
        setLeftText(getContext().getString(text));
    }

    public void setLeftSecondText(int text) {
        if (text == 0) {
            mLlSecondTitle.setVisibility(View.GONE);
            return;
        }
        setLeftSecondText(getContext().getString(text));
    }

    public void setLeftSecondText(String text) {
        if (TextUtils.isEmpty(text)) {
            mLlSecondTitle.setVisibility(View.GONE);
            return;
        }
        mLlSecondTitle.setVisibility(View.VISIBLE);
        mTvSecondTitle.setText(text);
    }

    public void setOnLeftSecondTxtClickListener(OnClickListener listener) {
        mTvSecondTitle.setOnClickListener(listener);
    }

    public void setLeftText(String text) {
        mLeftButton.setVisibility(View.GONE);
        if (TextUtils.isEmpty(text)) {
            mLeftTextView.setVisibility(View.GONE);
            return;
        }
        mLeftTextView.setText(text);
        mLeftTextView.setVisibility(View.VISIBLE);

    }

    /**
     * 设置字体
     *
     * @param typeface
     */
    public void setTitleTypeface(final Typeface typeface) {
        mTitleTextView.setTypeface(typeface);
    }

    /**
     * 使用资源设置标题
     *
     * @param resId
     */
    public void setTitleText(int resId) {
        String text = getResources().getString(resId);
        setTitleText(text);
    }

    /**
     * 获取当前标题文字
     *
     * @return
     */
    public String getTitle() {
        return mTitleTextView.getText().toString();
    }

    /**
     * 设置左侧按钮监听器
     *
     * @param listener
     */
    public void setOnLeftBtnClickListener(OnClickListener listener) {
        mLeftButton.setOnClickListener(listener);
    }

    public void setOnLeftTxtClickListener(OnClickListener listener) {
        mLeftTextView.setOnClickListener(listener);
    }

    /**
     * 设置右侧按钮监听器
     *
     * @param listener
     */
    public void setOnRightBtnClickListener(OnClickListener listener) {
        mRightButton.setOnClickListener(listener);
    }

    public void setOnRightTxtClickListener(OnClickListener listener) {
        mRightTxtView.setOnClickListener(listener);
    }

    /**
     * 设置附加按钮监听器
     *
     * @param listener
     */
    public void setOnSubjoinBtnClickListener(OnClickListener listener) {
        mSubjoinButton.setOnClickListener(listener);
    }

    public void setCompoundDrawable(TextView textView, int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        setCompoundDrawable(textView, drawable);
    }

    public void setCompoundDrawable(TextView textView, Drawable drawable) {
        setCompoundDrawable(textView, drawable, 4);
    }

    public void setCompoundDrawable(TextView textView, int resId, int drawablePadding) {
        Drawable drawable = getResources().getDrawable(resId);
        setCompoundDrawable(textView, drawable, drawablePadding);
    }

    public void setCompoundDrawable(TextView textView, Drawable drawable, int drawablePadding) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setCompoundDrawablePadding(ViewUtils.dip2px(getContext(), drawablePadding));
        textView.setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * 设置左侧按钮资源图片
     *
     * @param resId
     */
    public void setLeftBtnDrawable(int resId) {
        mLeftTextView.setVisibility(View.GONE);
        if (resId == 0) {
            mLeftButton.setVisibility(View.GONE);
            return;
        }
        mLeftButton.setVisibility(View.VISIBLE);
        mLeftButton.setImageResource(resId);
        mLeftButton.setOnClickListener(this);
    }

    /**
     * 设置左侧按钮资源图片
     *
     * @param resId
     */
    public void setRightBtnDrawable(int resId) {
        mRightTxtView.setVisibility(View.GONE);
        if (resId == 0) {
            mRightButton.setVisibility(View.GONE);
            return;
        }
        mRightButton.setVisibility(View.VISIBLE);
        mRightButton.setImageResource(resId);
    }

    /**
     * 设置附加按钮资源图片
     *
     * @param resId
     */
    public void setSubjoinBtnDrawable(int resId) {
        mSubjoinButton.setVisibility(View.VISIBLE);
        mSubjoinButton.setImageResource(resId);
    }

    public void setRightText(String text) {
        mRightButton.setVisibility(View.GONE);
        mRightTxtView.setVisibility(View.VISIBLE);
        mRightTxtView.setText(text);
    }

    public void setRightText(int textResId) {
        mRightButton.setVisibility(View.GONE);
        if (textResId == 0) {
            mRightTxtView.setVisibility(View.GONE);
            return;
        }
        mRightTxtView.setVisibility(View.VISIBLE);
        mRightTxtView.setText(getContext().getString(textResId));
    }

    public void setRightTextColor(int color) {
        mRightTxtView.setTextColor(color);
    }

    public void setRightSubText(String text) {
        mRightSubTextView.setVisibility(View.VISIBLE);
        mRightSubTextView.setText(text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_left_button
                && getContext() instanceof Activity) {
            hideSoftInput();
            BaseActivity activity = (BaseActivity) getContext();
            activity.finish();
        }
    }

    public void hideSoftInput() {
        InputMethodManager inputManger = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManger.hideSoftInputFromWindow(getWindowToken(), 0);
    }
}
