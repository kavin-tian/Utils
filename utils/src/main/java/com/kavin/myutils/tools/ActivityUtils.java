package com.kavin.myutils.tools;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.annotation.NonNull;

import java.util.List;

public class ActivityUtils {

    /**
     * Return the name of launcher activity.
     *
     * @return the name of launcher activity
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    /**
     * Return the name of launcher activity.
     *
     * @param pkg The name of the package.
     * @return the name of launcher activity
     */
    public static String getLauncherActivity(@NonNull final String pkg) {
        if (StringUtils.isSpace(pkg)) return "";
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        if (info == null || info.size() == 0) {
            return "";
        }
        return info.get(0).activityInfo.name;
    }


    /**
     * Finish all of activities.
     */
   /* public static void finishAllActivities() {
        finishAllActivities(false);
    }*/

    /**
     * Finish all of activities.
     *
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
 /*   public static void finishAllActivities(final boolean isLoadAnim) {
        List<Activity> activityList = UtilsActivityLifecycleImpl.INSTANCE.getActivityList();
        for (Activity act : activityList) {
            // sActivityList remove the index activity at onActivityDestroyed
            act.finish();
            if (!isLoadAnim) {
                act.overridePendingTransition(0, 0);
            }
        }
    }*/

}
