package com.example.user.moviedetails_middleproject;

import java.io.Serializable;

/**
 * Created by User on 18/07/2016.
 */
public class Movie implements Serializable {

    private long _id;
    private String title;
    private String plot;
    private String image;

    public Movie(long _id, String title, String plot, String image) {
        this._id = _id;
        this.title = title;
        this.plot = plot;
        this.image = image;
    }

    public Movie(String title, String plot, String image) {
        this.title = title;
        this.plot = plot;
        this.image = image;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

