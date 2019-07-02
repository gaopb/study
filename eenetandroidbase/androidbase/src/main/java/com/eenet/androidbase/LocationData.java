package com.eenet.androidbase;

import com.eenet.androidbase.bean.LocationBean;

/**
 * Created by yao23 on 2017/11/3.
 */

public class LocationData {

    private LocationBean mLocationBean;

    private static final LocationData ourInstance = new LocationData();

    public static LocationData getInstance() {
        return ourInstance;
    }

    private LocationData() {
    }

    public LocationBean getLocationBean() {
        return mLocationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        mLocationBean = locationBean;
    }

    public String getCurrentCity() {
        return mLocationBean !=null ? mLocationBean.getCurrentCity() : "";
    }

    public double getCurrentLatitude() {
        return mLocationBean !=null ? mLocationBean.getCurrentLatitude() : -1.0;
    }

    public double getCurrentLongitude() {
        return mLocationBean !=null ? mLocationBean.getCurrentLongitude() : -1.0;
    }

    public String getCurrentProvince() {
        return mLocationBean !=null ? mLocationBean.getCurrentProvince() : "";
    }

    public String getCurrentDistrict() {
        return mLocationBean !=null ? mLocationBean.getCurrentDistrict() : "";
    }

    public String getCurrentAddress() {
        return mLocationBean !=null ? mLocationBean.getCurrentAddress() : "";
    }

    public void setData(String province,String city,String district,double latitude,double longitude,String address) {
        mLocationBean = new LocationBean();
        mLocationBean.setCurrentProvince(province);
        mLocationBean.setCurrentCity(city);
        mLocationBean.setCurrentDistrict(district);
        mLocationBean.setCurrentLatitude(latitude);
        mLocationBean.setCurrentLongitude(longitude);
        mLocationBean.setCurrentAddress(address);
    }

}
