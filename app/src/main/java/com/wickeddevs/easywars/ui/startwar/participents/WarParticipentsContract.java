package com.wickeddevs.easywars.ui.startwar.participents;

import com.wickeddevs.easywars.base.PView;
import com.wickeddevs.easywars.base.Presenter;
import com.wickeddevs.easywars.data.model.Member;
import com.wickeddevs.easywars.data.model.api.ApiMember;
import com.wickeddevs.easywars.data.model.war.Participent;

import java.util.ArrayList;

/**
 * Created by 375csptssce on 9/12/16.
 */
public interface WarParticipentsContract {

    interface View extends PView {

        void removeMember(int position);

        void undoRemoveMember();

        void displayMemberList(ArrayList<Member> members, ArrayList<ApiMember> apiMembers);

        void displayThSelector();

        void displayMember(int place, String name, int thLevel);

        int getWarSize();

        void setRemainingText(String remainingText);

        void toggleLoading(boolean loading);

        void allowDone(boolean allow);

        void dismiss();

    }

    interface ViewListener extends Presenter<View> {

        void onCreate();

        void selectedParticipent(Participent participent, int position);

        void selectedTownHall(int thLevel);

        void pressedUndo();

        void pressedDone();
    }
}
