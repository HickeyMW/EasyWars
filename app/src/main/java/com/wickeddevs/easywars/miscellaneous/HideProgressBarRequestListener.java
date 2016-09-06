package com.wickeddevs.easywars.miscellaneous;

import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by 375csptssce on 9/6/16.
 */
public class HideProgressBarRequestListener implements RequestListener {

    ProgressBar progressBar;

    public HideProgressBarRequestListener(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        return false;
    }
}
