package cn.berfy.framework.support.views.pickPhoto;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import cn.berfy.framework.R;
import cn.berfy.framework.utils.LogUtil;

/**
 * @author 张全
 *         拍照选择弹出框
 */
public class PhotoPickSheet implements View.OnClickListener {

    private final String TAG = "PhotoPickSheet";
    private Activity mContext;
    private BottomSheetDialog dialog;
    private UploadPhoto uploadPhoto;
    private OnPhotoPickListener mOnPhotoPickListener;
    private OnOperateListener onOperateListener;
    private boolean operate;

    public PhotoPickSheet(Activity context) {
        mContext = context;
        uploadPhoto = new UploadPhoto(context);
        uploadPhoto.setPhotoChooser(new UploadPhoto.PhotoChooser() {
            @Override
            public void onPhotoChoose(String localPath, Bitmap bitmap) {
                if (null != mOnPhotoPickListener) {
                    mOnPhotoPickListener.pick(localPath, bitmap);
                }
            }
        });
        dialog = new BottomSheetDialog(context);
        View view = View.inflate(context, R.layout.upload_photo, null);
        view.findViewById(R.id.takePhoto).setOnClickListener(this);
        view.findViewById(R.id.pickPhoto).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!operate && null != onOperateListener) onOperateListener.onDialogCancel();
            }
        });
        dialog.setContentView(view);
    }

    public void show() {
        operate = false;
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setReqSize(int reqSize) {
        uploadPhoto.setReqSize(reqSize);
    }

    /**
     * 设置图片上传类型 0头像 1通用
     *
     * @param type
     */
    public void setUploadType(int type) {
        uploadPhoto.setUploadType(type);
    }


    public Bitmap getBitmap() {
        return uploadPhoto.getBitmap();
    }

    public String getUploadImg() {
        return uploadPhoto.getUploadImg();
    }

    public void setUploadPhoto(UploadPhoto uploadPhoto) {
        this.uploadPhoto = uploadPhoto;
    }

    public UploadPhoto getUploadPhoto() {
        return uploadPhoto;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.takePhoto) {
            LogUtil.i(TAG, "拍照");
            operate = true;
            if (null != onOperateListener) onOperateListener.onTakePhoto();
            else uploadPhoto.takePhotoAction();
        } else if (v.getId() == R.id.pickPhoto) {
            LogUtil.i(TAG, "图库");
            operate = true;
            if (null != onOperateListener) onOperateListener.onPickPhoto();
            else uploadPhoto.pickAlbumAction();
        } else if (v.getId() == R.id.cancel) {
            LogUtil.i(TAG, "取消");
            operate = false;
            dialog.dismiss();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        uploadPhoto.onActivityResult(requestCode, resultCode, data);
    }

    public void release() {
        uploadPhoto.release();
    }


    public void setOnOperateListener(OnOperateListener onOperateListener) {
        this.onOperateListener = onOperateListener;
    }


    public void setOnPhotoPickListener(OnPhotoPickListener onPhotoPickListener) {
        this.mOnPhotoPickListener = onPhotoPickListener;
    }

    /**
     * 图片上传
     */
    public interface OnPhotoPickListener {
        void pick(String localUrl, Bitmap bitmap);
    }

    /**
     * 操作
     */
    public interface OnOperateListener {
        /**
         * 拍照
         */
        void onTakePhoto();

        /**
         * 选择相册
         */
        void onPickPhoto();

        /**
         * 取消
         */
        void onDialogCancel();
    }

}
