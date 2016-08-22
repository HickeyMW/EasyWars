package wickeddevs.easywars.ui.startwar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wickeddevs.easywars.R;
import wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoFragment;

public class StartWarActivity extends AppCompatActivity implements BasicWarInfoFragment.BasicWarInfoFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_war);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameStartWar, new BasicWarInfoFragment()).commit();
    }

    @Override
    public void startWarInfo(int warSize, long startTime, String clanTag) {

    }

    @Override
    public void readyForNext() {

    }

    public interface WarSetupFragment {
        void getData();
    }
}
