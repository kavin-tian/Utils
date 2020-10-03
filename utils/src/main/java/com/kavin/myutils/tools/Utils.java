package com.kavin.myutils.tools;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import java.lang.reflect.Method;

public class Utils {


    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 反射的方式 获取当前应用的Application
     * 先使用ActivityThread里获取Application的方法，如果没有获取到，
     * 再使用AppGlobals里面的获取Application的方法
     * @return
     */
    public static Application getApp(){
        Application application = null;
        try{
            Class atClass = Class.forName("android.app.ActivityThread");
            Method currentApplicationMethod = atClass.getDeclaredMethod("currentApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
            Log.d("fw_create","curApp class1:"+application);
        }catch (Exception e){
            Log.d("fw_create","e:"+e.toString());
        }

        if(application != null)
            return application;

        try{
            Class atClass = Class.forName("android.app.AppGlobals");
            Method currentApplicationMethod = atClass.getDeclaredMethod("getInitialApplication");
            currentApplicationMethod.setAccessible(true);
            application = (Application) currentApplicationMethod.invoke(null);
            Log.d("fw_create","curApp class2:"+application);
        }catch (Exception e){
            Log.d("fw_create","e:"+e.toString());
        }

        return application;
    }

    /**
     * 无需上下文context， 获取Resources
     * @return
     */
    public static Resources getResources(){
        Resources resources = Resources.getSystem();
        return resources;
    }

/*    public interface OnAppStatusChangedListener {
        void onForeground(Activity activity);

        void onBackground(Activity activity);
    }

    public static class ActivityLifecycleCallbacks {

        public void onActivityCreated(@NonNull Activity activity) {*//**//*}

        public void onActivityStarted(@NonNull Activity activity) {*//**//*}

        public void onActivityResumed(@NonNull Activity activity) {*//**//*}

        public void onActivityPaused(@NonNull Activity activity) {*//**//*}

        public void onActivityStopped(@NonNull Activity activity) {*//**//*}

        public void onActivityDestroyed(@NonNull Activity activity) {*//**//*}

        public void onLifecycleChanged(@NonNull Activity activity, Lifecycle.Event event) {*//**//*}
    }*/


    public interface Consumer<T> {
        void accept(T t);
    }
}
