package com.example.abhishekkumar.popularmovies;

/**
 * Created by Abhishek Kumar on 02-10-2017.
 */

public class CategorizeMovie
{
    String title;
    String vote_count;
    String overview;
    String poster_path;
    String id;
    public CategorizeMovie(String n,String m,String g,String p,String i)
    {
        title=n;
        vote_count=m;
        overview=g;
        poster_path=p;
        id=i;
    }
}
