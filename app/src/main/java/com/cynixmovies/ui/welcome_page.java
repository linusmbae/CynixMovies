package com.cynixmovies.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cynixmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class welcome_page extends AppCompatActivity {
    @BindView(R.id.determinateBar)ProgressBar progressBar;
    @BindView(R.id.progress)TextView progress;
    private int counter=0;
    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_page);
        ButterKnife.bind(this);

        process();
    }

    private void process() {
//        counter++;
//        progress.setText(counter+" %");
//        Handler handler=new Handler();
//        progressBar.setMax(100);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    counter++;
//                    for (int i=0;i<100;i++){
//                        Thread.sleep(200);
//                        counter++;
//                        progress.setText(counter+" %");
//                    }
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        Intent intent = new Intent(mContext,Login.class);
        startActivity(intent);
    }


}