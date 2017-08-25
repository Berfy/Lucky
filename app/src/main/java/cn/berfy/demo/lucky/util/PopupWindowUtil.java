package cn.berfy.demo.lucky.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import cn.berfy.demo.lucky.R;
import cn.berfy.demo.lucky.ui.lucky.model.Lucky;
import cn.berfy.framework.base.BasePopupWindowUtil;
import cn.berfy.framework.support.colorpicker.ColorPickerDialog;
import cn.berfy.framework.support.views.pickPhoto.PhotoPickSheet;
import cn.berfy.framework.utils.LogUtil;

/**
 * Created by Berfy on 2017/6/19.
 */

public class PopupWindowUtil extends BasePopupWindowUtil {

    public PopupWindowUtil(Context context) {
        super(context);
    }

    public void showAddLucky(final OnAddLuckyListener onAddLuckyListener) {
        final View popView = View.inflate(mContext, R.layout.pop_add_lucky, null);
        mPopupWindow.setContentView(popView);
        final Lucky luckyCache = new Lucky();
        final ColorPickerDialog dialog = new ColorPickerDialog(mContext, 0xFF000000);
        dialog.setHexValueEnabled(true);
        dialog.setAlphaSliderVisible(true);
        dialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                LogUtil.e("颜色是啥", "" + color);
                luckyCache.setColor(color);
                popView.findViewById(R.id.v_color).setBackgroundColor(color);
            }
        });
        Switch swt = (Switch) popView.findViewById(R.id.swt);
        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                luckyCache.setShowImg(isChecked);
                popView.findViewById(R.id.layout_img).setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        ((EditText) popView.findViewById(R.id.edit_name)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                luckyCache.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        popView.findViewById(R.id.layout_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onAddLuckyListener) {
                    onAddLuckyListener.pickPhoto(new PhotoPickSheet.OnPhotoPickListener() {
                        @Override
                        public void pick(String localUrl, Bitmap bitmap) {
                            luckyCache.setImgPath(localUrl);
                            ImageLoader.getInstance().displayImage("file://" + localUrl, ((ImageView) popView.findViewById(R.id.iv_icon)), new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {
                                    popView.findViewById(R.id.tv_img_tip).setVisibility(View.VISIBLE);
                                    popView.findViewById(R.id.iv_icon).setVisibility(View.GONE);
                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    popView.findViewById(R.id.tv_img_tip).setVisibility(View.GONE);
                                    popView.findViewById(R.id.iv_icon).setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                        }
                    });
                }
            }
        });
        popView.findViewById(R.id.layout_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        popView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        popView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onAddLuckyListener) {
                    onAddLuckyListener.add(luckyCache);
                }
            }
        });
        show(((Activity) mContext).getWindow().getDecorView());
    }

    public interface OnAddLuckyListener {
        void pickPhoto(PhotoPickSheet.OnPhotoPickListener onPhotoPickListener);

        void add(Lucky lucky);
    }

}
