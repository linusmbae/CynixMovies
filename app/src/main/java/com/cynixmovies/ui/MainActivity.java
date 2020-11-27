package com.cynixmovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cynixmovies.Adapter.MoviesIconAdapter;
import com.cynixmovies.Constants.Constants;
import com.cynixmovies.R;
import com.cynixmovies.models.MoviesImage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String names;
    private String emails;

    @BindView(R.id.recyclerViewMovies)RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshIconLayout)SwipeRefreshLayout mRefresh;


    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;
    StorageTask storageTask;


    private ArrayList<MoviesImage> mMovieIcon;
    private MoviesIconAdapter mAdapter;
    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
//                    getSupportActionBar().setDisplayShowHomeEnabled(true);
//                    getSupportActionBar().setLogo(R.drawable.pofile);
//                    getSupportActionBar().setDisplayUseLogoEnabled(true);
                    getSupportActionBar().setTitle(user.getDisplayName());
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    names=user.getDisplayName();
                    emails=user.getEmail();
                } else {
                    getSupportActionBar().setTitle("Cynix Movies");
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                }
            }
        };

        rootNode = FirebaseDatabase.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference(Constants.FIREBASE_CHILD_MOVIE_IMAGE);
        reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MOVIE_IMAGE);

        mMovieIcon=new ArrayList<MoviesImage>();
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(MainActivity.this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        createAuthProgressDialog();
        getMovieIcons();
        refresh();
    }

    private void getMovieIcons() {
        mAuthProgressDialog.show();
        reference= FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_MOVIE_IMAGE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAuthProgressDialog.dismiss();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    MoviesImage moviesImage=dataSnapshot1.getValue(MoviesImage.class);
                    mMovieIcon.add(moviesImage);
                }
                if (mMovieIcon!=null){
                    mAdapter=new MoviesIconAdapter(MainActivity.this,mMovieIcon);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }else {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops!.. There is no data in the database")
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mAuthProgressDialog.dismiss();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops!.. We encountered an error kindly try again later")
                        .show();
            }
        });
    }

    private void refresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mMovieIcon=new ArrayList<MoviesImage>();
                mAuthProgressDialog.show();
                reference= FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MOVIE_IMAGE);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mAuthProgressDialog.dismiss();
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            MoviesImage moviesImage=dataSnapshot1.getValue(MoviesImage.class);
                            mMovieIcon.add(moviesImage);
                        }
                        if (mMovieIcon!=null){
                            mAdapter=new MoviesIconAdapter(MainActivity.this,mMovieIcon);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }else {
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops!.. There is no data in the database")
                                    .show();
                        }
                        mRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mAuthProgressDialog.dismiss();
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops!.. We encountered an error kindly try again later")
                                .show();
                    }
                });
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cynix_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutCynix) {
            logout();
            return true;
        }
        if (id == R.id.myProfile) {
            profile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void profile() {
        Intent intent = new Intent(MainActivity.this,UpdateProfile.class);
        startActivity(intent);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Cynix Movies");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
    }
}