package com.boss.cuncis.bukatoko.utils;

import android.view.Menu;

import com.boss.cuncis.bukatoko.R;

public class AuthState {

    public static void isLoggedIn(Menu menu) {
        menu.findItem(R.id.nav_notif).setVisible(true);
        menu.findItem(R.id.nav_transaski).setVisible(true);
        menu.findItem(R.id.nav_profil).setVisible(true);
        menu.findItem(R.id.nav_logout).setVisible(true);

        menu.findItem(R.id.nav_login).setVisible(false);
    }

    public static void isLoggedOut(Menu menu) {
        menu.findItem(R.id.nav_notif).setVisible(false);
        menu.findItem(R.id.nav_transaski).setVisible(false);
        menu.findItem(R.id.nav_profil).setVisible(false);
        menu.findItem(R.id.nav_logout).setVisible(false);

        menu.findItem(R.id.nav_login).setVisible(true);
    }

}
