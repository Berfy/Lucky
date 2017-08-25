package cn.berfy.demo.lucky;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import butterknife.BindView;
import cn.berfy.demo.lucky.ui.lucky.LuckysActivity;
import cn.berfy.demo.lucky.view.LuckyPanView;
import cn.berfy.framework.base.BaseActivity;
import cn.berfy.framework.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    @BindView(R.id.id_luckypan)
    LuckyPanView mLuckyPanView;
    @BindView(R.id.id_start_btn)
    ImageView mStartBtn;

    private Random mRandom;

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        mRandom = new Random();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mLuckyPanView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLuckyPanView.stop();
    }

    @Override
    protected void initView() {
        getTitleBar().setTitleBgColor(R.color.title);
        getTitleBar().setLeftBtnDrawable(0);
        getTitleBar().setRightText("设置");
        getTitleBar().setOnRightTxtClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithAnim(new Intent(mContext, LuckysActivity.class));
            }
        });
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLuckyPanView.getLucyNumber() > 0) {
                    if (!mLuckyPanView.isStart()) {
                        mStartBtn.setImageResource(R.drawable.stop);
                        mLuckyPanView.luckyStart(mRandom.nextInt(mLuckyPanView.getLucyNumber()));
//                    mLuckyPanView.luckyStart(1);
                    } else {
                        if (!mLuckyPanView.isShouldEnd()) {
                            mStartBtn.setImageResource(R.drawable.start);
                            mLuckyPanView.luckyEnd();
                        }
                    }
                } else {
                    ToastUtil.getInstance().showToast("请先设置礼物");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
