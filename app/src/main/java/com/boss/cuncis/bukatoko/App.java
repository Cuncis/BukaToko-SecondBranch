package com.boss.cuncis.bukatoko;

import android.app.Application;
import android.util.Log;

import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.db.SQLiteHelper;

import java.util.HashMap;

public class App extends Application {
    public static SQLiteHelper sqLiteHelper;

    public static PrefsManager prefsManager;
    public static HashMap<String, String> sessPref;

    @Override
    public void onCreate() {
        super.onCreate();
        sqLiteHelper = new SQLiteHelper(this);

        prefsManager = new PrefsManager(this);
        sessPref = prefsManager.getUserDetails();

        Log.d("_logBase", "onCreate: Test");
    }
}
