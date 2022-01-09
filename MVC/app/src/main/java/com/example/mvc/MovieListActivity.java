/**
 * @file MovieListActivity.java
 * @brief This activity is responsible for showing all movies data in the list format.
 * @author Shrikant
 * @date 14/04/2018
 */

package com.example.mvc;

import static com.example.mvc.network.ApiClient.API_KEY;
import static com.example.mvc.utils.GridSpacingItemDecoration.dpToPx;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvc.adapter.MoviesAdapter;
import com.example.mvc.model.Movie;
import com.example.mvc.model.MovieListResponse;
import com.example.mvc.network.ApiClient;
import com.example.mvc.utils.GridSpacingItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private static final String TAG = "MovieListActivity";

    private RecyclerView rvMovieList;
    private List<Movie> moviesList;
    private MoviesAdapter moviesAdapter;
    private ProgressBar pbLoading;
    private FloatingActionButton fabFilter;
    private TextView tvEmptyView;

    private int pageNo = 1;

    //Constants for load more
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private GridLayoutManager mLayoutManager;

    // Constants for filter functionality
    private String fromReleaseFilter = "";
    private String toReleaseFilter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Nano", "TIME: "+System.nanoTime());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        getData();
        initUI();
    }

    private final String API_KEY = "e79a859ffb63de1677a00f1b7665f552";
    private  final String LANGUAGE = "pt-BR";
    private  final String PAGE = "1";
    private List<Movie> resultMovies = new ArrayList<>();
    private void getData(){


        Call<MovieListResponse> call = ApiClient
                .getInstance()
                .getApi()
                .getPopularMovies(API_KEY, 1);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse > response) {
                if(response.isSuccessful() && response.body().getResults() !=null){
                    resultMovies = response.body().getResults();
                    MoviesAdapter moviesAdapter = new MoviesAdapter(MovieListActivity.this, resultMovies);
                    rvMovieList.setAdapter(moviesAdapter);
                    Log.i("Nano", "TIME: "+System.nanoTime());
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });
    }

    private void initUI() {

        rvMovieList = findViewById(R.id.rv_movie_list);

        moviesList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, moviesList);

        mLayoutManager = new GridLayoutManager(this, 2);
        rvMovieList.setLayoutManager(mLayoutManager);
        rvMovieList.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(moviesAdapter);

        tvEmptyView = findViewById(R.id.tv_empty_view);
    }


    public void showEmptyView() {

        rvMovieList.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);

    }

    public void hideEmptyView() {
        rvMovieList.setVisibility(View.VISIBLE);
        tvEmptyView.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Nano", "TIME: "+System.nanoTime());
    }
}
