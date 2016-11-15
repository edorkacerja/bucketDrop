package com.example.acerpc.bucketdrop;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AcerPC on 11/15/2016.
 */

public class AppRealm extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration rc = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(rc);

    }
}
