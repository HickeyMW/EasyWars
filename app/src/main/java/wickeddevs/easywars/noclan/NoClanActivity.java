package wickeddevs.easywars.noclan;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import wickeddevs.easywars.R;
import wickeddevs.easywars.databinding.ActivityNoClanBinding;

public class NoClanActivity extends AppCompatActivity {

    private ActivityNoClanBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_no_clan);
        mBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBinding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
