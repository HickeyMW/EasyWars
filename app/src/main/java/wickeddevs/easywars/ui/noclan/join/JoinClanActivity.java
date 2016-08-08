package wickeddevs.easywars.ui.noclan.join;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.ui.noclan.create.CreateClanActivity;

public class JoinClanActivity extends BasePresenterActivity<JoinClanContract.ViewListener> implements JoinClanContract.View {

    public final static String EXTRA_CLAN_TAG = "EXTRA_CLAN_TAG";

    @Inject
    public JoinClanContract.ViewListener presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_clan);
    }

    @Override
    public void displaySearchResult(ArrayList<ApiClan> apiClans) {

    }

    @Override
    public void displayDetailedClan(ApiClan apiClan) {

    }

    @Override
    public void navigateToJoiningClanUi() {

    }

    @Override
    public void navigateToNoClanUi() {

    }

    @Override
    protected JoinClanContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayToast(String toast) {

    }

    public static Intent createIntent(Context context, String clanTag) {
        Intent i = new Intent(context, JoinClanActivity.class);
        i.putExtra(EXTRA_CLAN_TAG, clanTag);
        return i;
    }
}
