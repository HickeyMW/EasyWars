package wickeddevs.easywars.ui.noclan;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.CreateRequest;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.databinding.ActivityNoClanBinding;
import wickeddevs.easywars.util.Utility;

public class NoClanActivity extends BasePresenterActivity<NoClanContract.ViewListener> implements NoClanContract.View {

    final static String TAG = "NoClanActivity";

    @Inject
    public NoClanContract.ViewListener presenter;
    private ActivityNoClanBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_no_clan);
        mBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.fragment_progress);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setContentView(R.layout.activity_no_clan);
                    }
                }, 2000);

            }
        });
        mBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setTitle("Setup");
    }

    @Override
    protected NoClanContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void showNoClanUi() {
    }

    @Override
    public void showHomeUi() {

    }

    @Override
    public void showCreateClanUi() {

    }

    @Override
    public void showJoinClanUi() {

    }

    @Override
    public void showAwaitingCreateUi(CreateRequest createRequest, ApiClan apiClan) {
        Log.i(TAG, "showAwaitingCreateUi: TEST");
    }

    @Override
    public void showAwaitingJoinUi(ApiClan apiClan) {

    }

    @Override
    public void displayCreateNotVerified() {

    }

    @Override
    public void displayJoinDenied() {

    }

    @Override
    public void toggleProgressBar(boolean loading) {


    }

    @Override
    public void displayToast(String toast) {

    }
}
