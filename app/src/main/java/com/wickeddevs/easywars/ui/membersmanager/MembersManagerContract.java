package com.wickeddevs.easywars.ui.membersmanager;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.Member;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/22/16.
 */

public interface MembersManagerContract {

    interface View extends PView {

        void displayMembers(ArrayList<Member> members);

        void toggleLoading(boolean loading);
    }

    interface ViewListener extends Presenter<View> {

        void onCreate();

        void selectedMember(Member member);

        void toggledAdmin(boolean isAdmin);
    }
}
