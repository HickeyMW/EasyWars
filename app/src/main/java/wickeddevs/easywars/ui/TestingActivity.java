package wickeddevs.easywars.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import wickeddevs.easywars.R;
import wickeddevs.easywars.ui.loadingsplash.LoadingSplashActivity;

public class TestingActivity extends AppCompatActivity {

    final static String TAG = "TestingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Button btnLoginHickeyMw = (Button) findViewById(R.id.btnLoginHickeyMw);
        Button btnLoginJoe = (Button) findViewById(R.id.btnLoginJoe);
        btnLoginHickeyMw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                fbAuth.signOut();
                fbAuth.signInWithEmailAndPassword("hickey.mw@gmail.com", "password").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(TestingActivity.this, LoadingSplashActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Log.e(TAG, "onClick: Failed to login as hickey.mw");
                        }
                    }
                });

            }
        });
        btnLoginHickeyMw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                fbAuth.signOut();
                fbAuth.signInWithEmailAndPassword("joe@gmail.com", "password").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(TestingActivity.this, LoadingSplashActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Log.e(TAG, "onClick: Failed to login as joe");
                        }
                    }
                });

            }
        });
    }
}
