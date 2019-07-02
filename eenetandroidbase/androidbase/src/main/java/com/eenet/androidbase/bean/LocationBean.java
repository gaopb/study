package com.eenet.androidbase.bean;

/**
 * Created by yao23 on 2017/11/3.
 */

public class LocationBean {

    private String mCurrentCity;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private String mCurrentProvince;
    private String mCurrentDistrict;
    private String mCurrentAddress;

    public String getCurrentCity() {
        return mCurrentCity;
    }

    public void setCurrentCity(String currentCity) {
        mCurrentCity = currentCity;
    }

    public double getCurrentLatitude() {
        return mCurrentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        mCurrentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return mCurrentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        mCurrentLongitude = currentLongitude;
    }

    public String getCurrentProvince() {
        return mCurrentProvince;
    }

    public void setCurrentProvince(String currentProvince) {
        mCurrentProvince = currentProvince;
    }

    public String getCurrentDistrict() {
        return mCurrentDistrict;
    }

    public void setCurrentDistrict(String currentDistrict) {
        mCurrentDistrict = currentDistrict;
    }

    public String getCurrentAddress() {
        return mCurrentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        mCurrentAddress = currentAddress;
    }
}
