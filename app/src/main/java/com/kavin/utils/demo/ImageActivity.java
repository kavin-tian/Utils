package com.kavin.utils.demo;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.constants.MemoryConstants;
import com.kavin.myutils.tools.ConvertUtils;
import com.kavin.myutils.tools.ImageUtils;
import com.kavin.myutils.tools.SizeUtils;

import java.io.File;
import java.util.ArrayList;

public class ImageActivity extends CheckPermissionsActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        logo = findViewById(R.id.logo);
    }

    @Override
    protected ArrayList<String> addNeedPermissionList(ArrayList<String> permissions) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissions;
    }

    public void toRound(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap toRound = ImageUtils.toRound(bitmap);
        logo.setImageBitmap(toRound);
    }

    public void toRoundCorner(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap toRound = ImageUtils.toRound(bitmap);
        int color = getResources().getColor(R.color.colorPrimary);
        Bitmap circleBorder = ImageUtils.addCircleBorder(toRound, SizeUtils.dp2px(15), color);
        logo.setImageBitmap(circleBorder);
    }

    public void toGray(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap toGray = ImageUtils.toGray(bitmap);
        logo.setImageBitmap(toGray);
    }

    /**
     * 水印会跟随图片缩放
     */
    public void addTextWatermark(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        int color = getResources().getColor(R.color.colorWhite);
        int px = SizeUtils.sp2px(18);
        Bitmap toGray = ImageUtils.addTextWatermark(bitmap, "hello！", px, color, 0, 0);
        logo.setImageBitmap(toGray);
    }

    public void stackBlur(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        int px = SizeUtils.dp2px(20);
        Bitmap toGray = ImageUtils.stackBlur(bitmap, px);
        logo.setImageBitmap(toGray);
    }


    public void save(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        saveImage(bitmap);
    }


    public void getImageType(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "logo.jpg";
        ImageUtils.ImageType imageType = ImageUtils.getImageType(path);
        Toast.makeText(this, "imageType: " + imageType, Toast.LENGTH_SHORT).show();
    }


    public void compressByScale(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap scaleBitmap = ImageUtils.compressByScale(bitmap, 100, 100);
        saveImage(scaleBitmap);
    }

    public void compressByQuality(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        byte[] bytes = ImageUtils.compressByQuality(bitmap, 0); //0 占用内存最小， 100占用内存最大
        Bitmap bytes2Bitmap = ImageUtils.bytes2Bitmap(bytes);
        saveImage(bytes2Bitmap);
    }

    public void compressBySampleSize(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        Bitmap sampleSizeBitmap = ImageUtils.compressBySampleSize(bitmap, 2);
        saveImage(sampleSizeBitmap);
    }

    public void getSize(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "logo.jpg";
        int[] size = ImageUtils.getSize(path);
        int w = size[0];
        int h = size[1];

        File file = new File(path);
        long length = file.length();
        float memorySize = (float) ConvertUtils.byte2MemorySize(length, MemoryConstants.KB);

        System.out.println(length);
        System.out.println(memorySize);

        Toast.makeText(this, "w: " + w + "\n" + "h: " + h + "\n" + "fileSize: " + memorySize + "KB", Toast.LENGTH_SHORT).show();
    }

    private boolean saveImage(Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "logo.jpg";
        boolean save = ImageUtils.save(bitmap, path, CompressFormat.JPEG);
        Toast.makeText(this, "save: " + save + "\n" + path, Toast.LENGTH_SHORT).show();
        return save;
    }
}
