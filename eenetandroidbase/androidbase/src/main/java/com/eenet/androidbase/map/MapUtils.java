package com.eenet.androidbase.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoma on 2017/11/7.
 */

public class MapUtils {

    private static final String GEO_AK = "hQdGuqElXDfnVslGzx8gj3Qg354ZbFEZ";

    private static final String GEO_SK = "SFaQ9ao64AwrTbl3OFztfGunKyv93YOG";

    /**
     * 获取地理编码请求参数
     * @param address
     * @return
     */
    public static String getGeoRequestParams(String address){
        Map<String,String> params = new LinkedHashMap<>();
        params.put("address",address);
        params.put("output","json");
        params.put("ak",GEO_AK);
        try {
            String paramsStr = toQueryString(params);
            String wholeStr = new String("/geocoder/v2/?" + paramsStr + GEO_SK);
            String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
            String sn = MD5(tempStr);
            return paramsStr + "&sn=" + sn;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取反向地理编码请求参数
     * @param lat
     * @param lng
     * @return
     */
    public static String getReverseGeoRequestParams(String lat,String lng){
        Map<String,String> params = new LinkedHashMap<>();
        params.put("location",lat+","+lng);
        params.put("output","json");
        params.put("pois","1");
        params.put("ak",GEO_AK);
        try {
            String paramsStr = toQueryString(params);
            String wholeStr = new String("/geocoder/v2/?" + paramsStr + GEO_SK);
            String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
            String sn = MD5(tempStr);
            return paramsStr + "&sn=" + sn;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "";
    }

    private static String toQueryString(Map<?,?> params) throws UnsupportedEncodingException{
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : params.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        String queryStr =  queryString.toString();
//        queryStr = queryStr.replaceAll("-","");
        return queryStr;
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * 打开百度地图
     */
    public static void openBaiduMap(Context context, RouteBean bean){
        if(bean == null){
            return;
        }
        if(isInstall(context,"com.baidu.BaiduMap")){
            try {
                context.startActivity(Intent.parseUri(
                        String.format("intent://map/direction?origin=latlng:%1$f,%2$f|name:%3$s"+
                                        "&destination=latlng:%4$f,%5$f|name:%6$s&mode=driving&region=%7$s&src=%8$s#Intent;" +
                                        "scheme=bdapp;package=com.baidu.BaiduMap;end",
                                bean.getStartLat(),
                                bean.getStartLng(),
                                bean.getStartName(),
                                bean.getEndLat(),
                                bean.getEndLng(),
                                bean.getEndName(),
                                bean.getRegion(),
                                getAppName(context))
                        ,0));
            } catch (URISyntaxException e) {
                e.printStackTrace();
                openBaiduBrowser(context,bean);
            }
        } else {
            openBaiduBrowser(context,bean);
        }
    }

    /**
     * 通过浏览器打开百度地图
     * @param context
     * @param bean
     */
    public static void openBaiduBrowser(Context context,RouteBean bean){
        startActivity(context,String.format("http://api.map.baidu.com/direction?origin=latlng:%1$f,%2$f|name:%3$s" +
                        "&destination=latlng:%4$f,%5$f|name:%6$s&mode=driving&region=%7$s&output=html" +
                        "&src=%8$s",
                bean.getStartLat(),
                bean.getStartLng(),
                bean.getStartName(),
                bean.getEndLat(),
                bean.getEndLng(),
                bean.getEndName(),
                bean.getRegion(),
                getAppName(context)));
    }

    /**
     * 通过浏览器打开高德地图
     * @param context
     * @param bean
     */
    public static void openGdBrowser(Context context,RouteBean bean){
        startActivity(context,String.format("http://uri.amap.com/navigation?from=%1$f,%2$f,%3$s&to=%4$f,%5$f,%6$s&mode=car&src=%7$s",
                bean.getStartLng(),
                bean.getStartLat(),
                bean.getStartName(),
                bean.getEndLng(),
                bean.getEndLat(),
                bean.getEndName(),
                getAppName(context)));
    }


    /**
     * 打开高德地图
     */
    public static void openGaodeMap(Context context,RouteBean bean){
        if(isInstall(context,"com.autonavi.minimap")){
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format(
                                        "androidamap://route?sourceApplication=%1$s&slat=%2$f&slon=%3$f&sname=%4$s&dlat=%5$f&dlon=%6$f&dname=%7$s&dev=0&m=0&t=2",
                                        getAppName(context),
                                        bean.getStartLat(),
                                        bean.getStartLng(),
                                        bean.getStartName(),
                                        bean.getEndLat(),
                                        bean.getEndLng(),
                                        bean.getEndName())));
                intent.setPackage("com.autonavi.minimap");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e){
                e.printStackTrace();
                openGdBrowser(context,bean);
            }

        } else{
            openGdBrowser(context,bean);
        }
    }

    /**
     * 百度地图坐标转换成高德地图坐标
     * @param lat
     * @param lng
     * @return
     */
    public static double[] gpsConvert(double lat, double lng) {
        double x = lng - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * 3.14159265358979324 * 3000.0 / 180.0);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * 3.14159265358979324 * 3000.0 / 180.0);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }

    public static void startActivity(Context context,String uriString){
        if(context == null || TextUtils.isEmpty(uriString)){
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(uriString));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /***
     * 是否安装某程序
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstall(Context context, String packageName){
        try {
            if(TextUtils.isEmpty(packageName)){
                return false;
            }
            List< PackageInfo > allPackage = context.getPackageManager().getInstalledPackages(0);
            for(PackageInfo packageInfo:allPackage){
                if(packageName.equals(packageInfo.packageName)){
                    return true;
                }
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getAppName(Context context){
        PackageInfo info = getPackageInfo(context);
        return info == null ? "" : (info.applicationInfo == null ? "" :info.applicationInfo.name);
    }

    private static PackageInfo getPackageInfo(Context context){
        PackageInfo pi = null;
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            if (pm != null) {
                try {
                    pi = pm.getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return pi;
    }

    public static Point getDisplaySize(Context context){
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(wm != null) {
            wm.getDefaultDisplay().getSize(point);
        }
        return point;
    }

    public static double parseDouble(String value){
        return parseDouble(value,0);
    }

    public static double parseDouble(String value,double defaultValue){
        try {
            return Double.parseDouble(value);
        } catch (Exception e){
            return defaultValue;
        }
    }

}
