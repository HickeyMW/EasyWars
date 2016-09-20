package com.wickeddevs.easywars.dagger.module;

import dagger.Module;
import dagger.Provides;
import com.wickeddevs.easywars.dagger.scope.ActivityScope;
import com.wickeddevs.easywars.data.service.contract.ApiService;
import com.wickeddevs.easywars.data.service.contract.ChatService;
import com.wickeddevs.easywars.data.service.contract.ClanService;
import com.wickeddevs.easywars.data.service.contract.CreateClanService;
import com.wickeddevs.easywars.data.service.contract.JoinClanService;
import com.wickeddevs.easywars.data.service.contract.UserService;
import com.wickeddevs.easywars.data.service.contract.VersionService;
import com.wickeddevs.easywars.data.service.contract.WarService;
import com.wickeddevs.easywars.ui.attacks.AttacksContract;
import com.wickeddevs.easywars.ui.attacks.AttacksPresenter;
import com.wickeddevs.easywars.ui.home.HomeContract;
import com.wickeddevs.easywars.ui.home.HomePresenter;
import com.wickeddevs.easywars.ui.home.chat.ChatContract;
import com.wickeddevs.easywars.ui.home.chat.ChatPresenter;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerContract;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerPresenter;
import com.wickeddevs.easywars.ui.home.war.clanoverview.ClanOverviewContract;
import com.wickeddevs.easywars.ui.home.war.clanoverview.ClanOverviewPresenter;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesContract;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesPresenter;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsContract;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsPresenter;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashContract;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashPresenter;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanContract;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanPresenter;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanContract;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanPresenter;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanContract;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanPresenter;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanContract;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanPresenter;
import com.wickeddevs.easywars.ui.shared.search.SearchClansContract;
import com.wickeddevs.easywars.ui.shared.search.SearchClansPresenter;
import com.wickeddevs.easywars.ui.startwar.info.WarInfoContract;
import com.wickeddevs.easywars.ui.startwar.info.WarInfoPresenter;
import com.wickeddevs.easywars.ui.startwar.participents.WarParticipentsContract;
import com.wickeddevs.easywars.ui.startwar.participents.WarParticipentsPresenter;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderContract;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderPresenter;
import com.wickeddevs.easywars.ui.warbase.WarBaseContract;
import com.wickeddevs.easywars.ui.warbase.WarBasePresenter;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    LoadingSplashContract.ViewListener providesLoadingSplashPresenter(LoadingSplashPresenter loadingSplashPresenter) {
        return loadingSplashPresenter;
    }


    @Provides
    @ActivityScope
    ChatContract.ViewListener providesChatContractPresenter(ChatPresenter chatPresenter) {
        return chatPresenter;
    }

    @Provides
    @ActivityScope
    SearchClansContract.ViewListener providesSearchClansPresenter(SearchClansPresenter searchClansPresenter) {
        return searchClansPresenter;
    }

    @Provides
    @ActivityScope
    CreateClanContract.ViewListener providesCreateClanPresenter(CreateClanPresenter createClanPresenter) {
        return createClanPresenter;
    }

    @Provides
    @ActivityScope
    JoinClanContract.ViewListener providesJoinClanPresenter(JoinClanPresenter joinClanPresenter) {
        return joinClanPresenter;
    }

    @Provides
    @ActivityScope
    VerifyCreateClanContract.ViewListener providesCreatingClanPresenter(VerifyCreateClanPresenter verifyCreateClanPresenter) {
        return verifyCreateClanPresenter;
    }

    @Provides
    @ActivityScope
    VerifyJoinClanContract.ViewListener providesJoiningClanPresenter(VerifyJoinClanPresenter verifyJoinClanPresenter) {
        return verifyJoinClanPresenter;
    }

    @Provides
    @ActivityScope
    JoinRequestsContract.ViewListener providesJoinRequestsPresenter(JoinRequestsPresenter joinRequestsPresenter) {
        return joinRequestsPresenter;
    }

    @Provides
    @ActivityScope
    HomeContract.ViewListener providesHomePresenter(HomePresenter homePresenter) {
        return homePresenter;
    }

    @Provides
    @ActivityScope
    WarEnemyBasesContract.ViewListener providesWarPresenter(WarEnemyBasesPresenter warEnemyBasesPresenter) {
        return warEnemyBasesPresenter;
    }

    @Provides
    @ActivityScope
    WarOrderContract.ViewListener providesWarOrderPresenter(WarOrderPresenter warOrderPresenter) {
        return warOrderPresenter;
    }

    @Provides
    @ActivityScope
    WarBaseContract.ViewListener providesWarBasePresenter(WarBasePresenter warBasePresenter) {
        return warBasePresenter;
    }

    @Provides
    @ActivityScope
    WarViewPagerContract.ViewListener providesWarViewPagerPresenter(WarViewPagerPresenter warViewPagerPresenter) {
        return warViewPagerPresenter;
    }

    @Provides
    @ActivityScope
    WarInfoContract.ViewListener providesWarInfoPresenter(WarInfoPresenter warInfoPresenter) {
        return warInfoPresenter;
    }

    @Provides
    @ActivityScope
    ClanOverviewContract.ViewListener providesClanOverviewPresenter(ClanOverviewPresenter clanOverviewPresenter) {
        return clanOverviewPresenter;
    }

    @Provides
    @ActivityScope
    WarParticipentsContract.ViewListener providesWarParticipentsPresenter(WarParticipentsPresenter warParticipentsPresenter) {
        return warParticipentsPresenter;
    }

    @Provides
    @ActivityScope
    AttacksContract.ViewListener providesAttacksPresenter(AttacksPresenter attacksPresenter) {
        return attacksPresenter;
    }
}
