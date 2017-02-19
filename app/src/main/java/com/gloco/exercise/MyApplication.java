package com.gloco.exercise;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.securepreferences.SecurePreferences;

/**
 * Created by "Tuan Nguyen" on 11/10/2016.
 */

public class MyApplication extends Application {

    private static final String PREF_FILE_NAME = "my_preferences.xml";

    private static final String PREF_PASSWORD = "inex_123";

    private static Context context;

    private static SecurePreferences mSecurePrefs;

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static SharedPreferences getMyPreferences() {
        if (mSecurePrefs == null) {
            synchronized (MyApplication.class) {
                if (mSecurePrefs == null) {
                    mSecurePrefs = new SecurePreferences(getAppContext(), PREF_PASSWORD,
                            PREF_FILE_NAME);
                    SecurePreferences.setLoggingEnabled(BuildConfig.DEBUG);
                }
            }
        }
        return mSecurePrefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyApplication.context = getApplicationContext();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }

}
