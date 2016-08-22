package wickeddevs.easywars.ui.warbase;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import javax.inject.Inject;

import wickeddevs.easywars.R;
import wickeddevs.easywars.adapters.ClaimCommentAdapter;
import wickeddevs.easywars.base.BasePresenterActivity;
import wickeddevs.easywars.dagger.Injector;
import wickeddevs.easywars.data.model.war.Base;
import wickeddevs.easywars.data.model.war.Comment;
import wickeddevs.easywars.databinding.ActivityWarBaseBinding;
import wickeddevs.easywars.ui.home.HomeContract;
import wickeddevs.easywars.util.Shared;

public class WarBaseActivity extends BasePresenterActivity<WarBaseContract.ViewListener> implements
        WarBaseContract.View {

    public final static String EXTRA_WAR_ID = "EXTRA_WAR_ID";
    public final static String EXTRA_BASE_ID = "EXTRA_BASE_ID";

    @Inject
    public WarBaseContract.ViewListener presenter;

    private ActivityWarBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_war_base);
        binding.btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pressedClaim();
            }
        });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = binding.etComment.getText().toString();
                presenter.sendComment(comment);
                binding.etComment.setText("");
            }
        });
    }

    @Override
    protected WarBaseContract.ViewListener getPresenter() {
        if(presenter == null){
            Injector.INSTANCE.inject(this);
        }
        return presenter;
    }

    @Override
    public String getWarId() {
        return getIntent().getStringExtra(EXTRA_WAR_ID);
    }

    @Override
    public String getBaseId() {
        return getIntent().getStringExtra(EXTRA_BASE_ID);
    }

    @Override
    public void setButtonClaimText(String text) {
        binding.btnClaim.setText(text);
    }

    @Override
    public void displayBase(Base base) {
        binding.tvName.setText(base.name);
        binding.ivTownHall.setImageResource(Shared.getThResource(base.townHallLevel));
        binding.rvClaimsComments.setLayoutManager(new LinearLayoutManager(this));
        binding.rvClaimsComments.setAdapter(new ClaimCommentAdapter(base.claims, base.comments));
    }

    @Override
    public void displayClaimsComments(ArrayList<String> claims, ArrayList<Comment> comments) {

    }

    @Override
    public void addClaim(String claim) {

    }

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public void toggleProgressBar(boolean loading) {

    }

    @Override
    public void displayMessage(String message) {

    }

    public static Intent createIntent(Context context, String warId, String baseId) {
        Intent i = new Intent(context, WarBaseActivity.class);
        i.putExtra(EXTRA_WAR_ID, warId);
        i.putExtra(EXTRA_BASE_ID, baseId);
        return i;
    }
}
