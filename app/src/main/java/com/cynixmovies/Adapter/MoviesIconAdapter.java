package com.cynixmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cynixmovies.R;
import com.cynixmovies.models.MoviesImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesIconAdapter extends RecyclerView.Adapter<MoviesIconAdapter.iconViewHolder>{

    private Context mContext;
    private ArrayList<MoviesImage> mMovieIcon;

    public MoviesIconAdapter(Context mContext, ArrayList<MoviesImage> mMovieIcon) {
        this.mContext = mContext;
        this.mMovieIcon = mMovieIcon;
    }

    @Override
    public MoviesIconAdapter.iconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_grid, parent, false);
        MoviesIconAdapter.iconViewHolder viewHolder = new MoviesIconAdapter.iconViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesIconAdapter.iconViewHolder holder, int position) {
        holder.bindMovieIcon(mMovieIcon.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieIcon.size();
    }

    public class iconViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movieIcon)ImageView mIcon;
        @BindView(R.id.iconDescription) TextView mDescription;
        @BindView(R.id.iconCategory) TextView mIconCategory;


        public iconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindMovieIcon(MoviesImage moviesImage){
            Picasso.get().load(moviesImage.getImage()).fit().into(mIcon);
            mDescription.setText(moviesImage.getDescription());
            mIconCategory.setText(moviesImage.getCategory());
        }
    }
}
