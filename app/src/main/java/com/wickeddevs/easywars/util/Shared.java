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

    public final static long MILIS_IN_ONE_DAY = 86400000;
    public final static long MILIS_IN_TWO_DAYS = 172800000;
    public final static long MILIS_IN_ONE_HOUR = 3600000;
    public final static long MILIS_IN_ONE_MINUTE = 60000;

    public static String formattedTimeRemainging(long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        String timeUntil = "";
        if (elapsedTime > MILIS_IN_ONE_DAY) {
            elapsedTime -= MILIS_IN_ONE_DAY;
            timeUntil += "Time until war end: ";
        } else {
            timeUntil += "Time until war start: ";
        }
        long hours = elapsedTime / MILIS_IN_ONE_HOUR;
        long remainingHours = 23 - hours;
        long remainingMinutes = 60 - ((elapsedTime - (hours * MILIS_IN_ONE_HOUR)) / MILIS_IN_ONE_MINUTE);
        if (remainingHours < 0) {
            timeUntil +=  "0:00";
        } else if (remainingMinutes == 60) {
            timeUntil += (remainingHours + 1) + ":00";
        } else if (remainingMinutes < 10) {
            timeUntil += remainingHours + ":0" + remainingMinutes;
        } else {
            timeUntil += remainingHours+ ":" + remainingMinutes;
        }

        return timeUntil;
    }

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
