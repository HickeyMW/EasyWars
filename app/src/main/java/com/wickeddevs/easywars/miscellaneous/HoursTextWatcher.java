package com.wickeddevs.easywars.miscellaneous;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by 375csptssce on 9/7/16.
 */
public class HoursTextWatcher implements TextWatcher {

    int newNumber = -1;
    int oldNumber = -1;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            oldNumber = Integer.valueOf(charSequence.toString());
        } catch (NumberFormatException e) {
            oldNumber = -1;
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            newNumber = Integer.valueOf(charSequence.toString());
        } catch (NumberFormatException e) {
            newNumber = -1;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (newNumber > 23)
        {
            String number = String.valueOf(oldNumber);
            editable.replace(0, editable.length(), number, 0, number.length());
        }
    }
}
