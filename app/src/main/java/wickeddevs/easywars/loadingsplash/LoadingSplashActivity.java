package wickeddevs.easywars.loadingsplash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wickeddevs.easywars.R;

public class LoadingSplashActivity extends AppCompatActivity {

    private LoadingSplashContract.ViewListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mListener.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mListener.stop();
    }
}
