package wickeddevs.easywars.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public abstract class BasePresenterFragment<T extends  Presenter> extends Fragment implements PView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().registerView(this);
        getPresenter().onAttach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDetach();
    }

    protected abstract T getPresenter();
}
