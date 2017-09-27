package com.kazlauski.matthew.beautifulbulldog;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Matthew on 9/27/2017.
 */

public class BulldogApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
