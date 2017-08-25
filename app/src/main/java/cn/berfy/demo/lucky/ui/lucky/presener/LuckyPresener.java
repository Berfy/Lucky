package cn.berfy.demo.lucky.ui.lucky.presener;

import android.content.Context;

import cn.berfy.demo.lucky.R;
import cn.berfy.demo.lucky.db.tab.Tab;
import cn.berfy.demo.lucky.ui.lucky.adapter.LuckysAdapter;
import cn.berfy.demo.lucky.ui.lucky.view.ILuckyView;
import cn.berfy.framework.utils.LogUtil;

/**
 * Created by Berfy on 2017/8/24.
 */

public class LuckyPresener {

    private Context mContext;
    private final String TAG = "LuckyPresener";
    private ILuckyView mLuckysView;
    private LuckysAdapter mAdapter;

    public LuckyPresener(ILuckyView iLuckyView) {
        mLuckysView = iLuckyView;
        mContext = iLuckyView.getContext();
        mAdapter = new LuckysAdapter(mContext);
        mLuckysView.getListView().setAdapter(mAdapter);
    }

    public void refreshData() {
        mAdapter.refresh(Tab.getInstances().getAllData());
        mAdapter.showDelete(mLuckysView.getIsDelete());
        updateButton();
    }

    public void updateButton() {
        if (Tab.getInstances().getAllData().size() > 0) {
            if (mLuckysView.getIsDelete()) {
                LogUtil.e(TAG, "删除");
                mLuckysView.getBtnAdd().setText(R.string.lucky_delete);
                mLuckysView.getTitleBarView().setRightText(R.string.finish);
            } else {
                LogUtil.e(TAG, "完成");
                mLuckysView.getBtnAdd().setText(R.string.lucky_add);
                mLuckysView.getTitleBarView().setRightText(R.string.delete);
            }
        } else {
            LogUtil.e(TAG, "没数据");
            mLuckysView.getTitleBarView().setRightText("");
        }
    }

    public void showDelete(boolean isDelete) {
        mAdapter.showDelete(isDelete);
        updateButton();
    }

    public LuckysAdapter getAdpater() {
        return mAdapter;
    }

}
