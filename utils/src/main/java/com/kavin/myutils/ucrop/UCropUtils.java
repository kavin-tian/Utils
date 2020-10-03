package com.kavin.myutils.ucrop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.ColorInt;
import androidx.core.content.FileProvider;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * 1.使用时请导入依赖
 * implementation 'com.github.yalantis:ucrop:2.2.6' //-轻巧的通用解决方案
 * implementation 'com.github.yalantis:ucrop:2.2.6-native' //-获得本机代码的功能以保持图像质量（apk大小约为1.5 MB），要求不高可以不导入
 * 2. 清单文件需要注册：android:name="com.yalantis.ucrop.UCropActivity"
 */
public class UCropUtils {
    private static final int GALLERY_REQUEST_CODE = 11;
    private static final int CAMERA_REQUEST_CODE = 22;
    private static Uri destinationUri;
    private static Uri sourceUri;
    private static int mToolbarColor = Color.BLUE;
    private static int mStatusBarColor = Color.BLUE;

    /**
     * 获取跳转至系统相册选择界面的Intent
     */
    public static void startImagePickerForResult(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public static void setBarColor(@ColorInt int toolbarColor, @ColorInt int statusBarColor) {
        mToolbarColor = toolbarColor;
        mStatusBarColor = statusBarColor;
    }

    /**
     * 跳转 UCrop 中裁剪
     *
     * @param activity
     */
    private static void startUCrop(Activity activity) {
        activity.getApplicationContext();
        //裁剪后保存到文件中
        destinationUri = Uri.fromFile(new File(activity.getCacheDir(), "myCroppedImage.jpg"));
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(com.yalantis.ucrop.UCropActivity.SCALE, com.yalantis.ucrop.UCropActivity.ROTATE, com.yalantis.ucrop.UCropActivity.ALL);
        //设置toolbar颜色
        //options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        options.setToolbarColor(mToolbarColor);
        //设置状态栏颜色
        //options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        options.setStatusBarColor(mStatusBarColor);
        //是否能调整裁剪框
        // options.setFreeStyleCropEnabled(true);
        uCrop.withOptions(options);
        uCrop.start(activity);
    }

    /**
     * 跳转 系统拍照
     *
     * @param activity
     */
    public static void startTakePhotoForResult(Activity activity) {
        Context context = activity.getApplicationContext();
        // 跳转到系统的拍照界面
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
        // File.separator为系统自带的分隔符 是一个固定的常量
        String mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";

        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
        //因为不是同一个app进行共享数据所以不能使用下面注释这个
        /*sourceUri = Uri.fromFile(new File(mTempPhotoPath));*/

        /*需要传递三个参数。第二个参数便是 Manifest 文件中注册 FileProvider 时设置的 authorities 属性值，
        第三个参数为要共享的文件，并且这个文件一定位于第二步我们在 path 文件中添加的子目录里面
        参考文章：https://blog.csdn.net/growing_tree/article/details/71190741*/
        sourceUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider",
                new File(mTempPhotoPath));

        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, sourceUri);
        activity.startActivityForResult(intentToTakePhoto, CAMERA_REQUEST_CODE);
    }

    /**
     * 处理裁剪结果
     * 1.处理系统相册返回的图片，2.处理Ucrop裁剪完成的图片
     * <p>
     * 请在Activity的onActivityResult中调用, 并传如你想设置图片的ImageView控件
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     * @param listener    裁剪完成回调接口
     */
    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, OnCropListener listener) {

        if (resultCode == UCropActivity.RESULT_OK) {   // 回传成功
            switch (requestCode) {

                case CAMERA_REQUEST_CODE: //从系统相机拍照返回
                    try {
                        // 裁剪
                        startUCrop(activity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                // 调用startImagePicker()时从系统相册返回的照片
                case GALLERY_REQUEST_CODE:
                    // 获取图片
                    try {
                        sourceUri = data.getData();
                        if (sourceUri != null) {
                            //对系统相册返回的图片进行裁剪
                            startUCrop(activity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                //使用UCrop裁剪后的图片
                case UCrop.REQUEST_CROP:
                    // 裁剪照片
                    final Uri croppedUri = UCrop.getOutput(data);
                    try {
                        if (croppedUri != null) {
                            Bitmap bit = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(croppedUri));
                            //获取裁剪完成的图片
                            listener.OnSucces(bit);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                //UCrop裁剪出错时调用
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    //裁剪失败
                    listener.OnFail(cropError);
                    break;

            }
        }
    }

    /**
     * 裁剪回调接口
     */
    public interface OnCropListener {
        abstract void OnSucces(Bitmap croppedBitmap);

        abstract void OnFail(Throwable cropError);
    }

}
