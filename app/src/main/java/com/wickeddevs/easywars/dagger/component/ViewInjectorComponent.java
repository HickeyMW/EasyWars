package com.wickeddevs.easywars.dagger.component;

import dagger.Component;
import com.wickeddevs.easywars.dagger.module.PresenterModule;
import com.wickeddevs.easywars.dagger.scope.ActivityScope;

import com.wickeddevs.easywars.ui.attacks.AttacksActivity;
import com.wickeddevs.easywars.ui.attacks.AttacksPresenter;
import com.wickeddevs.easywars.ui.home.HomeActivity;
import com.wickeddevs.easywars.ui.home.chat.ChatFragment;
import com.wickeddevs.easywars.ui.home.war.WarViewPagerFragment;
import com.wickeddevs.easywars.ui.home.war.clanoverview.ClanOverviewFragment;
import com.wickeddevs.easywars.ui.home.war.enemybases.WarEnemyBasesFragment;
import com.wickeddevs.easywars.ui.joinrequests.JoinRequestsActivity;
import com.wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;
import com.wickeddevs.easywars.ui.membersmanager.MembersManagerActivity;
import com.wickeddevs.easywars.ui.membersmanager.MembersManagerPresenter;
import com.wickeddevs.easywars.ui.noclan.create.CreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifycreate.VerifyCreateClanActivity;
import com.wickeddevs.easywars.ui.noclan.join.JoinClanActivity;
import com.wickeddevs.easywars.ui.noclan.verifyjoin.VerifyJoinClanActivity;
import com.wickeddevs.easywars.ui.shared.search.SearchClansActivity;
import com.wickeddevs.easywars.ui.startwar.info.WarInfoActivity;
import com.wickeddevs.easywars.ui.startwar.participents.WarParticipentsActivity;
import com.wickeddevs.easywars.ui.startwar.warorder.WarOrderActivity;
import com.wickeddevs.easywars.ui.warbase.WarBaseActivity;
import com.wickeddevs.easywars.ui.warsettings.WarSettingsActivity;

/**
 * Created by hicke_000 on 8/2/2016.
 */
@ActivityScope
@Component(dependencies = {ServiceComponent.class}, modules = PresenterModule.class)
public interface ViewInjectorComponent {

    void inject(LoadingSplashActivity loadingSplashActivity);

    void inject(ChatFragment chatFragment);

    void inject(SearchClansActivity searchClansActivity);

    void inject(CreateClanActivity createClanActivity);

    void inject(JoinClanActivity joinClanActivity);

    void inject(VerifyCreateClanActivity verifyCreateClanActivity);

    void inject(VerifyJoinClanActivity verifyJoinClanActivity);

    void inject(JoinRequestsActivity joinRequestsActivity);

    void inject(HomeActivity homeActivity);

    void inject(WarEnemyBasesFragment warEnemyBasesFragment);

    void inject(WarBaseActivity warBaseActivity);

    void inject(WarViewPagerFragment warViewPagerFragment);

    void inject(WarInfoActivity warInfoActivity);

    void inject(WarOrderActivity warOrderActivity);

    void inject(ClanOverviewFragment clanOverviewFragment);

    void inject(WarParticipentsActivity warParticipentsActivity);

    void inject(AttacksActivity attacksActivity);

    void inject(WarSettingsActivity warSettingsActivity);

    void inject(MembersManagerActivity membersManagerActivity);
}
