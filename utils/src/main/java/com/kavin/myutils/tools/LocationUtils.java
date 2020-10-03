package com.kavin.myutils.tools;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * 定位相关工具类
 */
public class LocationUtils {
    //圆周率
    private static double pi = 3.1415926535897932384626;

    //Krasovsky 1940 (北京54)椭球长半轴
    private static double a = 6378245.0;

    //椭球的偏心率
    private static double ee = 0.00669342162296594323;
    private static OnLocationChangeListener mListener;
    private static MyLocationListener myLocationListener;
    private static LocationManager mLocationManager;


    /**
     * 判断Gps是否可用
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断定位是否可用
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    public static boolean isLocationEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * 打开Gps设置界面
     */
    public static void openGpsSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        context.startActivity(intent);
    }


    /**
     * 注册
     * <p>
     * 使用完记得调用[.unRegisterLocation]
     * <p>
     * 需添加权限 `<uses-permission android:name="android.permission.INTERNET"/>`
     * <p>
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>`
     * <p>
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>`
     * <p>
     * 如果`minDistance`为0，则通过`minTime`来定时更新；
     * <p>
     * `minDistance`不为0，则以`minDistance`为准；
     * <p>
     * 两者都为0，则随时刷新。
     *
     * @param minTime     位置信息更新周期（单位：毫秒）
     * @param minDistance 位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）
     * @param listener    位置刷新的回调接口
     * @return `true`: 初始化成功<br></br>`false`: 初始化失败
     */
    public static boolean registerLocation(Context context, Long minTime, Long minDistance, OnLocationChangeListener listener) {
        if (listener == null) {
            return false;
        }
    /*  if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return false
        }*/
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mListener = listener;
        if (!isLocationEnabled(context)) {
            Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            return false;
        }

        String provider = mLocationManager.getBestProvider(getCriteria(), true);

        //val provider = mLocationManager .getBestProvider(criteria, true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) {
            listener.getLastKnownLocation(location);
        }
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }

        //Float.parseFloat(minDistance);
        mLocationManager.requestLocationUpdates(provider, minTime, (float) minDistance, myLocationListener);

        return true;
    }


    /**
     * 注销
     */
    public static void unRegisterLocation() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
    }


    /**
     * 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
     * 设置是否要求速度
     * 设置是否允许运营商收费
     * 设置是否需要方位信息
     * 设置是否需要海拔信息
     * 设置对电源的需求
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        //criteria.accuracy = Criteria.ACCURACY_FINE;
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        //criteria.isSpeedRequired = true;
        criteria.setSpeedRequired(true);
        // 设置是否允许运营商收费
        //criteria.isCostAllowed = true;
        criteria.setCostAllowed(true);
        //设置是否需要方位信息
        //criteria.isBearingRequired = true;
        criteria.setBearingRequired(true);
        //设置是否需要海拔信息
        //criteria.isAltitudeRequired = true;
        criteria.setAltitudeRequired(true);
        // 设置对电源的需求
        //criteria.powerRequirement = Criteria.POWER_LOW;
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;

    }


    /**
     * 根据经纬度获取地理位置
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return [Address]
     */
    public static Address getAddress(Context context, Double latitude, Double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            // Address addresses = geocoder.getFromLocation(latitude, longitude, 1);
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据经纬度获取所在国家
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在国家
     */
    public static String getCountryName(Context context, Double latitude, Double longitude) {
        Address address = getAddress(context, latitude, longitude);
        if (address == null) {
            return "unknown";
        } else {
            return address.getCountryName();
        }
    }


    /**
     * 根据经纬度获取所在地
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在地
     */
    public static String getLocality(Context context, Double latitude, Double longitude) {
        Address address = getAddress(context, latitude, longitude);
        if (address == null) {
            return "unknown";
        } else {
            return address.getLocality();
        }

    }

    /**
     * 根据经纬度获取所在街道
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在街道
     */
    public static String getStreet(Context context, Double latitude, Double longitude) {
        Address address = getAddress(context, latitude, longitude);
        if (address == null) {
            return "unknown";
        } else {
            return address.getAddressLine(0);
        }

    }


    /**
     * GPS坐标 转换成 角度
     * 例如 113.202222 转换成 113°12′8″
     *
     * @param location
     * @return
     */
    public static String gpsToDegree(Double location) {

        //val degree = floor(location);
        double degree = Math.floor(location);
        //val minute_temp = (location - degree) * 60
        double minute_temp = (location - degree) * 60;
        //val minute = floor(minute_temp)
        double minute = Math.floor(minute_temp);
        //double second = Math.floor((minute_temp - minute)*60);
        //val second = new DecimalFormat("#.##").format((minute_temp - minute) * 60);
        String second = new DecimalFormat("#.##").format((minute_temp - minute) * 60);
        //return (degree.toInt()).toString() + "°" + minute.toInt() + "′" + second + "″"
        String degreeStr = String.valueOf((int) degree);
        String minuteStr = String.valueOf((int) minute);
        return degreeStr + "°" + minuteStr + "′" + second + "″";
    }

//------------------------------------------坐标转换工具start--------------------------------------


    /**
     * 国际 GPS84 坐标系
     * 转换成
     * [国测局坐标系] 火星坐标系 (GCJ-02)
     * <p>
     * <p>
     * World Geodetic System ==> Mars Geodetic System
     *
     * @param lon 经度
     * @param lat 纬度
     * @return GPS实体类
     */
    public static Gps GPS84ToGCJ02(Double lon, Double lat) {
        if (outOfChina(lon, lat)) {
            return null;
        }
        Double dLat = transformLat(lon - 105.0, lat - 35.0);
        Double dLon = transformLon(lon - 105.0, lat - 35.0);
        Double radLat = lat / 180.0 * pi;
        Double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        Double sqrtMagic = Math.sqrt(magic);
        dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * pi);
        dLon = dLon * 180.0 / (a / sqrtMagic * Math.cos(radLat) * pi);
        Double mgLat = lat + dLat;
        Double mgLon = lon + dLon;
        return new Gps(mgLon, mgLat);
    }

    /**
     * [国测局坐标系] 火星坐标系 (GCJ-02)
     * 转换成
     * 国际 GPS84 坐标系
     *
     * @param lon 火星经度
     * @param lat 火星纬度
     */
    public static Gps GCJ02ToGPS84(Double lon, Double lat) {
        Gps gps = transform(lon, lat);
        Double lontitude = lon * 2 - gps.longitude;
        Double latitude = lat * 2 - gps.latitude;
        return new Gps(lontitude, latitude);
    }

    /**
     * 火星坐标系 (GCJ-02)
     * 转换成
     * 百度坐标系 (BD-09)
     *
     * @param ggLon 经度
     * @param ggLat 纬度
     */
    public static Gps GCJ02ToBD09(Double ggLon, Double ggLat) {
        Double z = Math.sqrt(ggLon * ggLon + ggLat * ggLat) + 0.00002 * Math.sin(ggLat * pi);
        Double theta = Math.atan2(ggLat, ggLon) + 0.000003 * Math.cos(ggLon * pi);
        Double bdLon = z * Math.cos(theta) + 0.0065;
        Double bdLat = z * Math.sin(theta) + 0.006;
        return new Gps(bdLon, bdLat);
    }

    /**
     * 百度坐标系 (BD-09)
     * 转换成
     * 火星坐标系 (GCJ-02)
     *
     * @param bdLon 百度*经度
     * @param bdLat 百度*纬度
     * @return GPS实体类
     */
    public static Gps BD09ToGCJ02(Double bdLon, Double bdLat) {
        Double x = bdLon - 0.0065;
        Double y = bdLat - 0.006;
        Double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        Double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        Double ggLon = z * Math.cos(theta);
        Double ggLat = z * Math.sin(theta);
        return new Gps(ggLon, ggLat);
    }

    /**
     * 百度坐标系 (BD-09)
     * 转换成
     * 国际 GPS84 坐标系
     *
     * @param bdLon 百度*经度
     * @param bdLat 百度*纬度
     * @return GPS实体类
     */
    public static Gps BD09ToGPS84(Double bdLon, Double bdLat) {
        Gps gcj02 = BD09ToGCJ02(bdLon, bdLat);
        return GCJ02ToGPS84(gcj02.longitude, gcj02.latitude);
    }

    /**
     * 国际 GPS84 坐标系
     * 转换成
     * 百度坐标系 (BD-09)
     *
     * @param gpsLon 国际 GPS84 坐标系下 的经度
     * @param gpsLat 国际 GPS84 坐标系下 的纬度
     * @return 百度GPS坐标
     */
    public static Gps GPS84ToBD09(Double gpsLon, Double gpsLat) {
        Gps gcj02 = GPS84ToGCJ02(gpsLon, gpsLat);
        return GCJ02ToBD09(gcj02.longitude, gcj02.latitude);
    }

    /**
     * 不在中国范围内
     *
     * @param lon 经度
     * @param lat 纬度
     * @return boolean值
     */
    public static boolean outOfChina(Double lon, Double lat) {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }

    /**
     * 转化算法
     *
     * @param lon 经度
     * @param lat 纬度
     * @return GPS信息
     */
    private static Gps transform(Double lon, Double lat) {
        if (outOfChina(lon, lat)) {
            return new Gps(lon, lat);
        }
        Double dLat = transformLat(lon - 105.0, lat - 35.0);
        Double dLon = transformLon(lon - 105.0, lat - 35.0);
        Double radLat = lat / 180.0 * pi;
        Double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        Double sqrtMagic = Math.sqrt(magic);
        dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * pi);
        dLon = dLon * 180.0 / (a / sqrtMagic * Math.cos(radLat) * pi);
        Double mgLat = lat + dLat;
        Double mgLon = lon + dLon;
        return new Gps(mgLon, mgLat);
    }

    /**
     * 纬度转化算法
     *
     * @param x x坐标
     * @param y y坐标
     * @return 纬度
     */
    private static Double transformLat(Double x, Double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 经度转化算法
     *
     * @param x x坐标
     * @param y y坐标
     * @return 经度
     */
    private static Double transformLon(Double x, Double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + (0.1 * Math.sqrt(Math.abs(x)));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }


    //------------------------------------------坐标转换工具end----------------------------------------


    /**
     * @author Tamsiree
     * @date 2017/6/19
     */
    public static class Gps {
        Double latitude = 0.0;
        Double longitude = 0.0;

        public Gps(Double longitude, Double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return "Gps{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }

    }

    public static boolean isMove(Location location, Location preLocation) {
        boolean isMove;
        if (preLocation != null) {
            //val speed = location.speed * 3.6;
            double speed = location.getSpeed() * 3.6;
            //double distance = location.distanceTo(preLocation).toDouble();
            double distance = location.distanceTo(preLocation);
            // val compass = Math.abs(preLocation.bearing - location.bearing).toDouble()
            double compass = Math.abs(preLocation.getBearing() - location.getBearing());
            double angle;
            if (compass > 180) {
                angle = 360 - compass;
            } else {
                angle = compass;
            }
            if (speed != 0.0) {
                if (speed < 35 && distance > 3 && distance < 10) {
                    isMove = angle > 10;
                } else {
                    isMove = speed < 40 && distance > 10 && distance < 100 ||
                            speed < 50 && distance > 10 && distance < 100 ||
                            speed < 60 && distance > 10 && distance < 100 ||
                            speed < 9999 && distance > 100;
                }
            } else {
                isMove = false;
            }
        } else {
            isMove = true;
        }
        return isMove;
    }


    interface OnLocationChangeListener {
        /**
         * 获取最后一次保留的坐标
         *
         * @param location 坐标
         */
        public void getLastKnownLocation(Location location);

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        public void onLocationChanged(Location location);

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        public void onStatusChanged(String provider, int status, Bundle extras); //位置状态发生改变
    }



    private static class MyLocationListener implements LocationListener {
        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }
        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("onStatusChanged", "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("onStatusChanged", "当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("onStatusChanged", "当前GPS状态为暂停服务状态");
                    break;
                default:
                    break;
            }

        }
        /**
         * provider被enable时触发此函数，比如GPS被打开
         */
        //override fun onProviderEnabled(provider: String) {}
        @Override
        public void onProviderEnabled(String provider) {

        }
        /**
         * provider被disable时触发此函数，比如GPS被关闭
         */
        //override fun onProviderDisabled(provider: String) {}
        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}
