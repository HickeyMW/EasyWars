package com.wickeddevs.easywars.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public abstract class BasePresenterFragment<T extends  Presenter> extends Fragment implements PView {

    protected Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().registerView(this);
    }

    @Override
    public void displayMessage(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected abstract T getPresenter();
}
