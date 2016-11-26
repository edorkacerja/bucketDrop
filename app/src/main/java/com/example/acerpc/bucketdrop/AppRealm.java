package com.example.acerpc.bucketdrop;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AcerPC on 11/15/2016.
 */


// ----- Class which sets the default Realm configurations while the app is running -----

public class AppRealm extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration rc = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(rc);

    }


    public static void save(Context context, int filterOption){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("filter", filterOption);
        editor.apply();
    }


    public static int load(Context context){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mySharedPreferences.getInt("filter", Filter.NONE);
    }
}
