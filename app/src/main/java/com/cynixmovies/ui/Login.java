package com.cynixmovies.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.cynixmovies.Dialogs.NewUserDialog;
import com.cynixmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity  implements NewUserDialog.CynixDialogListener{
    @BindView(R.id.newAcount) Button newAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser();
            }
        });
    }

    private void addNewUser() {
        NewUserDialog newUserDialog=new NewUserDialog();
        newUserDialog.show(getSupportFragmentManager(),"new user dialog");
    }

    @Override
    public void applyText(String TxtUserName, String TxtEmail, String TxtPassword) {
        if (TextUtils.isEmpty(TxtUserName)){
            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Username Field is Empty")
                    .show();
        }else if (TextUtils.isEmpty(TxtEmail)){
            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Email Field is Empty")
                    .show();
        }else if (TextUtils.isEmpty(TxtPassword)){
            new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Password Field is Empty")
                    .show();
        }else {
            String name=TxtUserName;
            String email=TxtEmail;
            String pass=TxtPassword;

            new SweetAlertDialog(Login.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success"+ name+email+pass)
                    .show();

//            Users users;
//            users=new Users(email,pass,name);
//
//
//            rootNode = FirebaseDatabase.getInstance();
//
//            reference = FirebaseDatabase
//                    .getInstance()
//                    .getReference(Constants.FIREBASE_CHILD_MYS_USERS);
//
//            DatabaseReference pushRef = reference.push();
//
//            pushRef.setValue(users);
        }
    }
}