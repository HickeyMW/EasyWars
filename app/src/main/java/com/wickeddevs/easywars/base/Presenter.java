package com.wickeddevs.easywars.base;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public interface Presenter<T extends PView> {

    public void registerView(T activity);
    public void onAttach();
    public void onDetach();
}
