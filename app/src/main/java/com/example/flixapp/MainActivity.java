package com.example.flixapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixapp.adapters.MovieAdapter;
import com.example.flixapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

// In android everyscreen is an activity
// Activities are where we write Java to handle user activity and change whats on the screen

public class MainActivity extends AppCompatActivity {


    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // creating a tag that wil allow us to log the data
    public static final String TAG = "MainActivity";
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // sets the content to be the stuff in activity_main.xml
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // set a layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //instancing the CodePath API Library
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "OnSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results"+ results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies:" + movies.size());


                } catch (JSONException e) {
                    Log.e(TAG,"error hit JSON exception");
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "OnFailure");
            }
        });


    }
}