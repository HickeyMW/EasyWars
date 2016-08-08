package wickeddevs.easywars.ui.noclan.join;

import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.service.contract.ApiService;
import wickeddevs.easywars.data.service.contract.JoinClanService;

/**
 * Created by hicke_000 on 8/3/2016.
 */
public class JoinClanPresenter implements JoinClanContract.ViewListener {

    private ApiClan apiClan;
    private String name;

    private JoinClanContract.View joinClanView;
    private ApiService apiService;
    private JoinClanService joinClanService;

    public JoinClanPresenter(ApiService apiService, JoinClanService joinClanService) {
        this.apiService = apiService;
        this.joinClanService = joinClanService;
    }

    @Override
    public void registerView(JoinClanContract.View activity) {
        joinClanView = activity;
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void search(String query) {

    }

    @Override
    public void selectedClan(ApiClan apiClan) {

    }

    @Override
    public void selectedName(String name) {

    }

    @Override
    public void createClan() {

    }
}
