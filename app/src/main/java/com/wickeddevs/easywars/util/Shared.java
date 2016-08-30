package com.wickeddevs.easywars.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wickeddevs.easywars.R;

/**
 * Created by 375csptssce on 8/22/16.
 */
public class Shared {

    final static String TAG = "FbInfo";

    public static int getThResource(int thLevel) {
        switch (thLevel) {
            case 1:
                return R.drawable.town_hall_1;
            case 2:
                return R.drawable.town_hall_2;
            case 3:
                return R.drawable.town_hall_3;
            case 4:
                return R.drawable.town_hall_4;
            case 5:
                return R.drawable.town_hall_5;
            case 6:
                return R.drawable.town_hall_6;
            case 7:
                return R.drawable.town_hall_7;
            case 8:
                return R.drawable.town_hall_8;
            case 9:
                return R.drawable.town_hall_9;
            case 10:
                return R.drawable.town_hall_10;
            case 11:
                return R.drawable.town_hall_11;
        }
        Log.e(TAG, "TH Level is not valid.");
        return -1;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
