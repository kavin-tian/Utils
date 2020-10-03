package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.tools.PathUtils;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
    }

    public void paths(View view) {
        String rootPath = PathUtils.getRootPath();
        String dataPath = PathUtils.getDataPath();
        String downloadCachePath = PathUtils.getDownloadCachePath();
        String internalAppDataPath = PathUtils.getInternalAppDataPath();
        String internalAppCodeCacheDir = PathUtils.getInternalAppCodeCacheDir();
        String internalAppCachePath = PathUtils.getInternalAppCachePath();
        String internalAppDbsPath = PathUtils.getInternalAppDbsPath();
        String internalAppFilesPath = PathUtils.getInternalAppFilesPath();
        String internalAppSpPath = PathUtils.getInternalAppSpPath();
        System.out.println(rootPath);
        System.out.println(dataPath);
        System.out.println(downloadCachePath);
        System.out.println(internalAppDataPath);
        System.out.println(internalAppCodeCacheDir);
        System.out.println(internalAppCachePath);
        System.out.println(internalAppDbsPath);
        System.out.println(internalAppFilesPath);
        System.out.println(internalAppSpPath);


        String externalStoragePath = PathUtils.getExternalStoragePath();
        String externalMusicPath = PathUtils.getExternalMusicPath();
        String externalPicturesPath = PathUtils.getExternalPicturesPath();
        String externalDownloadsPath = PathUtils.getExternalDownloadsPath();
        String externalAppDataPath = PathUtils.getExternalAppDataPath();
        String externalAppCachePath = PathUtils.getExternalAppCachePath();
        String externalAppFilesPath = PathUtils.getExternalAppFilesPath();
        String externalAppMusicPath = PathUtils.getExternalAppMusicPath();
        String externalAppPicturesPath = PathUtils.getExternalAppPicturesPath();
        String externalAppDownloadPath = PathUtils.getExternalAppDownloadPath();
        String rootPathExternalFirst = PathUtils.getRootPathExternalFirst();
        System.out.println(externalStoragePath);
        System.out.println(externalMusicPath);
        System.out.println(externalPicturesPath);
        System.out.println(externalDownloadsPath);
        System.out.println(externalAppDataPath);
        System.out.println(externalAppCachePath);
        System.out.println(externalAppFilesPath);
        System.out.println(externalAppMusicPath);
        System.out.println(externalAppPicturesPath);
        System.out.println(externalAppDownloadPath);
        System.out.println(rootPathExternalFirst);

    }
}
