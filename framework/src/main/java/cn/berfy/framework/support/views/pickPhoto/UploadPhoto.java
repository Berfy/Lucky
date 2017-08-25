package cn.berfy.framework.support.views.pickPhoto;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.berfy.framework.utils.Base64;
import cn.berfy.framework.utils.DeviceUtil;
import cn.berfy.framework.utils.FileUtils;
import cn.berfy.framework.utils.LogUtil;
import cn.berfy.framework.utils.ToastUtil;
import rx.functions.Action1;

/**
 * 照片选择器
 * 适配7.0
 *
 * @author Berfy
 */
public class UploadPhoto {

    private final String TAG = "UploadPhoto";
    private Activity mContext;
    private ImageView mIvPhoto;
    private UploadPhotoListener mUploadPhotoListener;
    private int mReqSize = 700;//图片压缩大小
    private File imgFile;
    private String mLocalPath;
    private Uri mCacheImgFile;
    private Bitmap bm;
    private int mUploadType;//0头像上传1普通图片通用上传
    public static final int GET_IMG_FROM_CAMERA = 11;//拍照获取照片
    public static final int GET_IMG_FROM_PHOTO_ALBUM = 12; // 从相册获取照片
    private int mTakePhoto = -1;//自定义的相机回调
    private int mPickPhoto = -1;//自定义的相册回调
    private boolean isReleased;

    public UploadPhoto(Fragment frag) {
        mContext = frag.getActivity();
    }

    public UploadPhoto(Activity ctx) {
        mContext = ctx;
    }

    /**
     * 设置图片压缩大小
     *
     * @param reqSize
     */
    public void setReqSize(int reqSize) {
        mReqSize = reqSize;
    }

    /**
     * 设置图片上传类型 0头像 1通用
     *
     * @param type
     */
    public void setUploadType(int type) {
        mUploadType = type;
    }

    /**
     * 从图库选择图片
     */
    public void pickAlbumAction(final int requestCode) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            pickAlbum(requestCode);
                        } else {
                            ToastUtil.getInstance().showToast("没有权限");
                            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
                        }
                    }
                });
    }

    /**
     * 从图库选择图片
     */
    public void pickAlbumAction() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            pickAlbum();
                        } else {
                            ToastUtil.getInstance().showToast("没有权限");
                            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
                        }
                    }
                });
    }

    private void pickAlbum(int requestCode) {
        mPickPhoto = requestCode;
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mContext.startActivityForResult(Intent.createChooser(intent, "选择照片"),
                    mPickPhoto);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.getInstance().showToast("无法打开相册");
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
        }
    }

    private void pickAlbum() {
        mPickPhoto = GET_IMG_FROM_PHOTO_ALBUM;
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mContext.startActivityForResult(Intent.createChooser(intent, "选择照片"),
                    mPickPhoto);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.getInstance().showToast("无法打开相册");
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
        }
    }

    /**
     * 拍照
     */
    public void takePhotoAction(final int requestCode) {
        mTakePhoto = requestCode;
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            openCamera(requestCode);
                        } else {
                            ToastUtil.getInstance().showToast("没有权限");
                            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
                        }
                    }
                });
    }

    /**
     * 拍照
     */
    public void takePhotoAction() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            openCamera();
                        } else {
                            ToastUtil.getInstance().showToast("没有权限");
                            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
                        }
                    }
                });
    }

    private void openCamera(int requestCode) {
        if (null == imgFile) {
            imgFile = createImageFile(System.currentTimeMillis() + "");
        }

        if (imgFile == null) {
            ToastUtil.getInstance().showToast("没有sd卡，暂时不能拍照");
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
            return;
        }
        try {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            mCacheImgFile = Uri.fromFile(imgFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mCacheImgFile = FileProvider.getUriForFile(mContext, DeviceUtil.getPackageName(mContext) + ".fileprovider", imgFile);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, mCacheImgFile);
            mContext.startActivityForResult(intentFromCapture, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.getInstance().showToast("无法调用照相机");
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
        }
    }

    private void openCamera() {
        LogUtil.e(TAG, "openCamera");
        mTakePhoto = GET_IMG_FROM_CAMERA;
        if (null == imgFile) {
            imgFile = createImageFile(System.currentTimeMillis() + "");
        }

        if (imgFile == null) {
            ToastUtil.getInstance().showToast("没有sd卡，暂时不能拍照");
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
            return;
        }
        try {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mCacheImgFile = Uri.fromFile(imgFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mCacheImgFile = FileProvider.getUriForFile(mContext, DeviceUtil.getPackageName(mContext) + ".fileprovider", imgFile);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                ClipData clip =
//                        ClipData.newUri(ctx.getContentResolver(), "A photo", contentUri);
//                intentFromCapture.setClipData(clip);
//                intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            } else {
//                List<ResolveInfo> resInfoList =
//                        ctx.getPackageManager()
//                                .queryIntentActivities(intentFromCapture, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolveInfo : resInfoList) {
//                    String packageName = resolveInfo.activityInfo.packageName;
//                    ctx.grantUriPermission(packageName, contentUri,
//                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                }
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, mCacheImgFile);
            mContext.startActivityForResult(intentFromCapture, mTakePhoto);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.getInstance().showToast("无法调用照相机");
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == mTakePhoto) {
                LogUtil.e(TAG, "mTakePhoto" + mCacheImgFile);
                compressImg(true);
            } else if (requestCode == mPickPhoto) {
                LogUtil.e(TAG, "mPickPhoto" + mCacheImgFile);
                if (null == data) {
                    if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
                    return;
                }
                mCacheImgFile = data.getData();
                compressImg(false);
            }
        } else {
            if (null != photoChooser) photoChooser.onPhotoChoose(null, null);
        }
    }

    private File createImageFile(String fileName) {
        if (!FileUtils.avaiableMedia()) {
            return null;
        }
        try {
            File fileDir = null;
            File externalDataDir = mContext.getExternalCacheDir();
            fileDir = new File(externalDataDir,
                    "app" + File.separator + "photos");

            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File[] files = fileDir.listFiles();
            if (null != files) {
                for (File item : files) {
                    if (null != item && item.exists()) {
                        item.delete();
                    }
                }
            }
            StringBuffer pathBuff = new StringBuffer(fileDir.getCanonicalPath());
            pathBuff.append(File.separatorChar).append(fileName + ".jpg");
            File imageFile = new File(pathBuff.toString());
            if (imageFile.exists() && imageFile.canRead()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
            return imageFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 压缩图片
     */
    private void compressImg(boolean isTakePhoto) {
        LogUtil.e(TAG, "compressImg");
        if (null == mCacheImgFile) {
            ToastUtil.getInstance().showToast("照片获取失败");
            return;
        }
        if (null == imgFile) {
            imgFile = createImageFile(System.currentTimeMillis() + "");
        }
        if (imgFile == null) {
            ToastUtil.getInstance().showToast("没有sd卡，暂时不能拍照");
            return;
        }
        LogUtil.e(TAG, "文件大小" + imgFile.getAbsolutePath() + "   " + imgFile.length());
        mLocalPath = imgFile.getAbsolutePath();
        if (isTakePhoto) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                bm = GetPhotoUtil.resizeBitmap(imgFile.getAbsolutePath(), mReqSize);
            } else {
                bm = GetPhotoUtil.resizeBitmap(mContext, mCacheImgFile, mReqSize);
            }
        } else {
            bm = GetPhotoUtil.resizeBitmap(mContext, mCacheImgFile, mReqSize);
        }

        if (null != bm) {
            GetPhotoUtil.saveBitmap2File(bm, imgFile);
        }
        if (null != photoChooser) {
            photoChooser.onPhotoChoose(imgFile.getAbsolutePath(), bm);
        }
        if (bm == null) {
            ToastUtil.getInstance().showToast("获取图片失败");
            return;
        }
    }

    public String getUploadImg() {
        return getImg(bm);
    }

    public Bitmap getBitmap() {
        return bm;
    }

    public static String getImg(Bitmap bitmap) {
        try {
            byte[] bytes = getBytes(bitmap);
            if (null != bytes) {
                return Base64.encode(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deletePhoto(Context ctx, Uri targetUri) {
        if (null == ctx || targetUri == null) return;
        try {
            ctx.getContentResolver().delete(targetUri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void recycleBitmap(Bitmap bm) {
        try {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bm = null;
        }
    }

    public void reset() {
        if (null != imgFile) {
            imgFile.delete();
        }
        imgFile = null;
        mCacheImgFile = null;
        recycleBitmap(bm);
    }

    /**
     * 解决拍照后在相册中找不到的问题
     */
    private static Uri addImageGallery(Context ctx, File file) {
        if (null == file) return null;
        try {

            String filePath = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), file.getAbsolutePath(), file.getName(), "");
            return Uri.parse(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void release() {
        isReleased = true;
        reset();
    }

    // ------------------------上传图片

    public interface UploadPhotoListener {
        void failed(String errMsg);

        void onLoadBitmap(ImageView imageView, String localUrl, Bitmap bitmap);
    }

    public void setImageView(ImageView imageView) {
        mIvPhoto = imageView;
    }

    public void setUploadListener(UploadPhotoListener listener) {
        this.mUploadPhotoListener = listener;
    }

    //-------------WebView FileChooser--------------------
    public void setPhotoChooser(PhotoChooser photoChooser) {
        this.photoChooser = photoChooser;
    }

    public static interface PhotoChooser {
        void onPhotoChoose(String localPath, Bitmap bitmap);
    }

    private PhotoChooser photoChooser;
}
