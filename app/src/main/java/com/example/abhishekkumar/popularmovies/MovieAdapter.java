package com.example.abhishekkumar.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
    private final ForecastAdapterOnClickHandler mClickHandler;
    int clickedPosition;
    public interface ForecastAdapterOnClickHandler {
        void onClick(CategorizeMovie CategorizeMovie,int ide);
    }

    Context context;
    private static String ur="http://image.tmdb.org/t/p/w780/";
    CategorizeMovie[] movieArray;
    ImageView imageView;
    public MovieAdapter(ForecastAdapterOnClickHandler clickHandler )
    {
        mClickHandler = clickHandler;

    }
    public void setData(CategorizeMovie[] movieCategorizer)
    {
        movieArray=movieCategorizer;
      
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        context=parent.getContext();
        int layoutIdForListItem=R.layout.detail;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(layoutIdForListItem,parent,false);
        MovieViewHolder viewHolder=new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {

        //clickedPosition=position;
        String web=ur+movieArray[position].poster_path;
        Picasso.with(context).load(web).into(imageView);


    }

    @Override
    public int getItemCount() {
        if(movieArray.length>-1)
            return movieArray.length;
        else
            return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.image_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
             clickedPosition=getAdapterPosition();
            mClickHandler.onClick(movieArray[clickedPosition],clickedPosition);


        }
    }
}
