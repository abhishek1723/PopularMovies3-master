package com.example.abhishekkumar.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ForecastAdapterOnClickHandler
{
    int yoyo=1;
    RecyclerView recyclerView;
    MovieAdapter MovieAdapter;
    private ProgressBar mLoadingIndicator;
    private static String urls = "http://api.themoviedb.org/3/movie/popular";
    private static String urlr = "http://api.themoviedb.org/3/movie/top_rated";
    final static String PARAM_QUERY = "api_key";
    final static String apikey="fb11cd6ac05c44b23052dc8434b6c72c";
    CategorizeMovie[] CategorizeMovies;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected=item.getItemId();
        if (menuItemThatWasSelected==R.id.action_sort_rating)
        {
            yoyo=0;
            MovieAdapter.setData(null);
            workeds();
            return true;
        }
        if (menuItemThatWasSelected==R.id.action_sort_popularity)
        {
            MovieAdapter.setData(null);
            yoyo=1;
            work();
            return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view) ;
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MovieAdapter=new MovieAdapter(this);
        work();
    }

    public void work()
    {
        recyclerView.setAdapter(null);
        CategorizeMovies=null;
        MovieAdapter.setData(null);
        URL query= uriBuilder(urls);
        new queryTask().execute(query);
    }
    public void workeds()
    {
        recyclerView.setAdapter(null);
        CategorizeMovies=null;
        MovieAdapter.setData(null);
        URL query= uriBuilder(urlr);
        new queryTask().execute(query);
    }

    public URL uriBuilder(String uri)
    {
        Uri builtUri=Uri.parse(uri).buildUpon()
                .appendQueryParameter(PARAM_QUERY,apikey)
                .build();
        URL url=null;
        try
        {
            url=new URL(builtUri.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return url;
    }
    @Override
    public void onClick(CategorizeMovie CategorizeMovieed,int idm) {
        Intent startChildActivityIntent=new Intent(MainActivity.this,secondActivity.class);
        Bundle extras = new Bundle();
        extras.putString("ide",CategorizeMovieed.id);
        extras.putInt("democheck",yoyo);
        startChildActivityIntent.putExtras(extras);
        startActivity(startChildActivityIntent);
    }
    private class queryTask extends AsyncTask<URL,Void,String>
    {
        @Override
        protected String doInBackground(URL... params)
        {
            URL searchURL=params[0];
            String searchResults=null;
            try
            {
                searchResults=getResponseFromHttpUrl(searchURL);
                if(searchResults!=null)
                {
                    try
                    {
                        JSONObject jsonObj=new JSONObject(searchResults);
                        JSONArray contacts=jsonObj.getJSONArray("results");
                        CategorizeMovies=new CategorizeMovie[contacts.length()];
                        for (int i=0;i<=contacts.length();i++)
                        {
                            JSONObject c=contacts.getJSONObject(i);
                            String id = c.getString("id");
                            String title = c.getString("title");
                            String vote_count = c.getString("vote_count");
                            String overview = c.getString("overview");
                            String poster_path=c.getString("poster_path");
                            CategorizeMovies[i]=new CategorizeMovie(title,vote_count,overview,poster_path,id);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Abhishek","Chinmaya");
            if(s!=null &&!s.equals(""))
            {
                StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                MovieAdapter=new MovieAdapter(MainActivity.this);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                MovieAdapter.setData(CategorizeMovies);
                recyclerView.setAdapter(MovieAdapter);
       }

        }

        @Override
        protected void onPreExecute()
        {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException
    {

        HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {

                return scanner.next();
            } else {

                return null;
            }
        }
        finally
        {
            urlConnection.disconnect();
        }
    }
}


