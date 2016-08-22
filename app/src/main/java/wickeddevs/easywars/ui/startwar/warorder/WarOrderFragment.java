package wickeddevs.easywars.ui.startwar.warorder;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.adapters.ClanMembersAdapter;
import wickeddevs.easywars.adapters.TownHallAdapter;
import wickeddevs.easywars.base.BasePresenterFragment;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.api.ApiClan;
import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.databinding.FragmentWarOrderBinding;
import wickeddevs.easywars.ui.startwar.basicinfo.BasicWarInfoContract;
import wickeddevs.easywars.util.Shared;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarOrderFragment extends BasePresenterFragment<WarOrderContract.ViewListener>
        implements WarOrderContract.View {

    @Inject
    public WarOrderContract.ViewListener presenter;

    private FragmentWarOrderBinding binding;
    private WarOrderFragmentListener listener;
    private ClanMembersAdapter clanMembersAdapter;

    ArrayList<Base> bases = new ArrayList<>();
    int remaining;
    int position;

    public WarOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_war_order, container, false);
        binding.rvThLevels.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnDone.setVisibility(View.INVISIBLE);
                bases.remove(bases.size() - 1);
                Base base = bases.get(bases.size() - 1);
                clanMembersAdapter.undo();
                remaining++;
                binding.tvLastAdded.setText(String.valueOf(bases.size()) + ". " + base.name);
                binding.tvRemaining.setText("Remaining: " + String.valueOf(remaining));
                binding.ivLastAdded.setImageResource(Shared.getThResource(base.townHallLevel));
                if (remaining == listener.getWarSize()) {
                    binding.btnUndo.setVisibility(View.INVISIBLE);
                }
            }
        });
        binding.rvThLevels.setAdapter(new TownHallAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int thLevel = binding.rvThLevels.getChildAdapterPosition(view) + 1;
                binding.btnUndo.setVisibility(View.VISIBLE);
                String name = clanMembersAdapter.getMember(position);
                int baseNumber = bases.size() + 1;
                Base base = new Base(thLevel, name);
                binding.tvLastAdded.setText(String.valueOf(baseNumber) + ". " + name);
                binding.ivLastAdded.setImageResource(Shared.getThResource(thLevel));
                bases.add(base);
                remaining--;
                binding.tvRemaining.setText("Remaining: " + String.valueOf(remaining));
                if (remaining == 0) {
                    binding.btnDone.setVisibility(View.VISIBLE);
                }
                clanMembersAdapter.remove(position);
                binding.rvEnemyNames.setAdapter(clanMembersAdapter);
                binding.rvThLevels.setVisibility(View.INVISIBLE);
                binding.rvEnemyNames.setVisibility(View.VISIBLE);
            }
        }));
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.baseInfo(bases);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WarOrderFragmentListener) {
            listener = (WarOrderFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement WarOrderFragmentListener");
        }
    }

    @Override
    protected WarOrderContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public void displayApiClan(ApiClan apiClan) {
        Glide.with(getContext()).load(apiClan.badgeUrls.medium).centerCrop().into(binding.ivEnemyBadge);
        binding.tvEnemyName.setText(apiClan.name);
        binding.tvEnemyTag.setText(apiClan.tag);
        remaining = listener.getWarSize();
        binding.tvRemaining.setText("Remaining: " + String.valueOf(remaining));
        binding.rvEnemyNames.setLayoutManager(new LinearLayoutManager(getContext()));
        clanMembersAdapter = new ClanMembersAdapter(apiClan.getMemberNames(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = binding.rvEnemyNames.getChildLayoutPosition(view);
                binding.rvThLevels.setVisibility(View.VISIBLE);
                binding.rvEnemyNames.setVisibility(View.INVISIBLE);
            }
        });
        binding.rvEnemyNames.setAdapter(clanMembersAdapter);

    }

    @Override
    public String getClanTag() {
        return listener.getEnemyTag();
    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayMessage(String message) {

    }


    public interface WarOrderFragmentListener {
        void baseInfo(ArrayList<Base> bases);
        int getWarSize();
        String getEnemyTag();
    }
}
