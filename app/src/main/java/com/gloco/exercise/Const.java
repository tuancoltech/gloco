package com.gloco.exercise;

/**
 * Created by Tuan Nguyen on 1/22/2017.
 */

public class Const {

    /**
     * Network error codes
     */

    public static final int ERROR_UNSUCCESSFUL = 1;

    public static final int ERROR_UNKNOWN = ERROR_UNSUCCESSFUL + 1;

    public static final int ERROR_NETWORK = ERROR_UNSUCCESSFUL + 2;

    /**
     * Request location code
     */
    public static final int REQ_CODE_LOCATION = 100;

    public static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    /**
     * Bundle extras keys
     */
    public static final String BUNDLE_EXTRAS_DOCTORS = "bundle_extras_doctors";

}
