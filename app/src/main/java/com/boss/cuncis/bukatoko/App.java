package com.boss.cuncis.bukatoko;

import android.app.Application;
import android.util.Log;

import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.db.SQLiteHelper;

public class App extends Application {
    public static PrefsManager prefsManager;
    public static SQLiteHelper sqLiteHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        prefsManager = new PrefsManager(this);
        sqLiteHelper = new SQLiteHelper(this);

        Log.d("_logBase", "onCreate: Test");
    }
}
