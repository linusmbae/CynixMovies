package com.cynixmovies.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cynixmovies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProfile extends AppCompatActivity {
    @BindView(R.id.UserName2)TextInputEditText UName;
    @BindView(R.id.email2)TextInputEditText UMail;
    @BindView(R.id.password2)TextInputEditText UPass;
    @BindView(R.id.update)Button update;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profiles);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    UName.setText(user.getDisplayName());
                    UMail.setText(user.getEmail());
                    getSupportActionBar().setTitle(user.getDisplayName());
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                } else {
                    getSupportActionBar().setTitle("Cynix Movies");
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                }
            }
        };

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= UName.getText().toString();
                String email=UMail.getText().toString();
                String pass=UPass.getText().toString();
                new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(email +name+pass)
                        .show();


            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
