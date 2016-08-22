package wickeddevs.easywars.ui.noclan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wickeddevs.easywars.R;
import wickeddevs.easywars.databinding.ActivityNoClanBinding;
import wickeddevs.easywars.ui.shared.search.SearchClansActivity;

public class NoClanActivity extends AppCompatActivity {

    final static String TAG = "NoClanActivity";

    private ActivityNoClanBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_no_clan);
        mBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchClansActivity.createIntent(NoClanActivity.this, SearchClansActivity.STARTED_FOR_CREATE));

            }
        });
        mBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchClansActivity.createIntent(NoClanActivity.this, SearchClansActivity.STARTED_FOR_JOIN));
            }
        });
    }
}
