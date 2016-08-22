package wickeddevs.easywars.ui.startwar;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import wickeddevs.easywars.R;
import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.data.model.war.War;
import wickeddevs.easywars.data.service.firebase.FbWarService;
import wickeddevs.easywars.databinding.ActivityStartWarBinding;
import wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoFragment;
import wickeddevs.easywars.ui.startwar.warorder.WarOrderFragment;

public class StartWarActivity extends AppCompatActivity implements BasicWarInfoFragment.BasicWarInfoFragmentListener,
        WarOrderFragment.WarOrderFragmentListener {

    private ActivityStartWarBinding binding;
    private int warSize;

    private War war = new War();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_war);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameStartWar, new BasicWarInfoFragment()).commit();
    }

    @Override
    public void startWarInfo(int warSize, long startTime, String clanName, String clanTag) {
        war.enemyName = clanName;
        war.enemyTag = clanTag;
        war.startTime = startTime;
        this.warSize = warSize;
        getSupportFragmentManager().beginTransaction().replace(R.id.frameStartWar, new WarOrderFragment()).commit();
    }

    @Override
    public void baseInfo(ArrayList<Base> bases) {
        war.bases = bases;
        new FbWarService().startWar(war);
        finish();
    }

    @Override
    public int getWarSize() {
        return warSize;
    }

    @Override
    public String getEnemyTag() {
        return war.enemyTag;
    }
}
