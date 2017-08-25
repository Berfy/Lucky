package cn.berfy.demo.lucky.ui.lucky;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.berfy.demo.lucky.R;
import cn.berfy.demo.lucky.db.tab.Tab;
import cn.berfy.demo.lucky.ui.lucky.model.Lucky;
import cn.berfy.demo.lucky.ui.lucky.presener.LuckyPresener;
import cn.berfy.demo.lucky.ui.lucky.view.ILuckyView;
import cn.berfy.demo.lucky.util.PopupWindowUtil;
import cn.berfy.framework.base.BaseActivity;
import cn.berfy.framework.support.views.pickPhoto.PhotoPickSheet;
import cn.berfy.framework.utils.ToastUtil;
import cn.berfy.framework.view.TitleBarView;

/**
 * Created by Berfy on 2017/8/23.
 * 礼物列表
 */
public class LuckysActivity extends BaseActivity implements ILuckyView {

    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    private PopupWindowUtil mPopupWindowUtil;
    private LuckyPresener mLuckyPresener;
    private PhotoPickSheet mPhotoPickSheet;
    private boolean mIsCanDelete;//是否可删除

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected int initContentViewById() {
        return R.layout.activity_luckys;
    }

    @Override
    protected void initVariable() {
        mPopupWindowUtil = new PopupWindowUtil(mContext);
        mPhotoPickSheet = new PhotoPickSheet(mContext);
    }

    @Override
    protected void initView() {
        getTitleBar().setTitleBgColor(R.color.title);
        getTitleBar().setOnRightTxtClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCanDelete = !mIsCanDelete;
                mLuckyPresener.showDelete(mIsCanDelete);
            }
        });
        mLuckyPresener = new LuckyPresener(this);
        mLuckyPresener.refreshData();
    }

    @OnClick({R.id.btn_add})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (mIsCanDelete) {
                    for (Lucky lucky : mLuckyPresener.getAdpater().getList()) {
                        if (lucky.isSelect()) {
                            Tab.getInstances().delete(lucky);
                        }
                    }
                    mIsCanDelete = false;
                    mLuckyPresener.refreshData();
                } else {
                    mPopupWindowUtil.showAddLucky(new PopupWindowUtil.OnAddLuckyListener() {
                        @Override
                        public void pickPhoto(final PhotoPickSheet.OnPhotoPickListener onPhotoPickListener) {
                            mPhotoPickSheet.setOnPhotoPickListener(new PhotoPickSheet.OnPhotoPickListener() {
                                @Override
                                public void pick(String localUrl, Bitmap bitmap) {
                                    mPhotoPickSheet.dismiss();
                                    onPhotoPickListener.pick(localUrl, bitmap);
                                }
                            });
                            mPhotoPickSheet.show();
                        }

                        @Override
                        public void add(Lucky lucky) {
                            if (TextUtils.isEmpty(lucky.getName())) {
                                ToastUtil.getInstance().showToast("请填写礼物名称");
                                return;
                            }
                            lucky.setName(lucky.getName());
                            if (lucky.isShowImg() && TextUtils.isEmpty(lucky.getImgPath())) {
                                ToastUtil.getInstance().showToast("请选择图片");
                                return;
                            }
                            mPopupWindowUtil.dismiss();
                            Tab.getInstances().add(lucky);
                            mLuckyPresener.refreshData();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public ListView getListView() {
        return mListView;
    }

    public Button getBtnAdd() {
        return mBtnAdd;
    }

    @Override
    public TitleBarView getTitleBarView() {
        return getTitleBar();
    }

    @Override
    public boolean getIsDelete() {
        return mIsCanDelete;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoPickSheet.onActivityResult(requestCode, resultCode, data);
    }
}
