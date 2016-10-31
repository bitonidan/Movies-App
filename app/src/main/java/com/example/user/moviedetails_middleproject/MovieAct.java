package com.example.user.moviedetails_middleproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MovieAct extends AppCompatActivity implements View.OnClickListener, Serializable {
    MovieDBHelper helpr;
    Movie movie;
    private EditText title, plot, image;
    private String title2, plot2, image2;
    public ImageView pic;
    private long id;
    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        title = (EditText) findViewById(R.id.Title);
        plot = (EditText) findViewById(R.id.Plot);
        image = (EditText) findViewById(R.id.URL);
        pic= (ImageView) findViewById(R.id.imageView);

        movie = (Movie) getIntent().getSerializableExtra("DetMov");
        if (movie != null) {
            title.setText(movie.getTitle().toString());
            plot.setText(movie.getPlot().toString());
            image.setText(movie.getImage().toString());

            title2 = title.getText().toString();
            plot2 = plot.getText().toString();
            image2 = image.getText().toString();
        } else {
            title2 = title.getText().toString();
            plot2 = plot.getText().toString();
            image2 = image.getText().toString();
        }

        findViewById(R.id.butURL).setOnClickListener(this);
        findViewById(R.id.butsave).setOnClickListener(this);




        id = getIntent().getLongExtra("Movie_id", -1);
        helpr = new MovieDBHelper(this);
        if (id != -1) {
            movie = helpr.getmoviebyId(id);
            title.setText(movie.getTitle());
            plot.setText(movie.getPlot());
            image.setText(movie.getImage());

        }


    }

    @Override
    public void onClick(View v) {
        Intent intent=getIntent();
        check=intent.getStringExtra("flag");
        switch (v.getId()) {

            case R.id.butURL:
                GetPosterTask posterTask = new GetPosterTask();
                posterTask.setPic(pic);
                posterTask.execute(image.getText().toString());
                break;

            case R.id.butsave:
                if (check.equals("add")||check.equals("web")){
                    title2 = title.getText().toString();
                    plot2 = plot.getText().toString();
                    image2 = image.getText().toString();
                    helpr.insertmovie(new Movie(title2,plot2,image2));
                    finish();
                }else {
                    title2 = title.getText().toString();
                    plot2 = plot.getText().toString();
                    image2 = image.getText().toString();
                    Movie movie=new Movie(title2,plot2,image2);
                    helpr.updateMovie(id,movie);
                    finish();
                }
                break;

        }


    }




}
