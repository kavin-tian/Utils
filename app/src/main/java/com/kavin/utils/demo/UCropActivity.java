package com.kavin.utils.demo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.ucrop.UCropUtils;

import java.util.ArrayList;


public class UCropActivity extends CheckPermissionsActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucrop);
        imageView = findViewById(R.id.image);

    }

    /**
     * 拍照需要获取的权限
     *
     * @return
     */
    @Override
    protected ArrayList<String> addNeedPermissionList(ArrayList<String> permissions) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        return permissions;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //第二步：1.处理系统相册返回的图片，2.处理Ucrop裁剪完成的图片
        UCropUtils.onActivityResult(this, requestCode, resultCode, data, new UCropUtils.OnCropListener() {
            @Override
            public void OnSucces(Bitmap croppedBitmap) {
                imageView.setImageBitmap(croppedBitmap);
            }

            @Override
            public void OnFail(Throwable cropError) {
                Toast.makeText(UCropActivity.this, "cropError", Toast.LENGTH_SHORT).show();
            }
        });

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void pick(View view) {
        //第一步：跳转系统相册选择图片
        UCropUtils.startImagePickerForResult(this);
    }


    public void takePhoto(View view) {
        UCropUtils.startTakePhotoForResult(this);
    }
}
