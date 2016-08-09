package wickeddevs.easywars.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by hicke_000 on 8/2/2016.
 */
public abstract class BasePresenterActivity<T extends Presenter> extends AppCompatActivity implements PView {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().registerView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onAttach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onDetach();
    }

    protected abstract T getPresenter();

    protected Toast getToast() {
        if (toast != null) {
            toast.cancel();
        }
        return toast;
    }
}
