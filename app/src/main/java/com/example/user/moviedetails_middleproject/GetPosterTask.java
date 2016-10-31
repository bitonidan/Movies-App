package com.example.user.moviedetails_middleproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 31/07/2016.
 */
public class GetPosterTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView pic ;

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageURL = params[0];
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageURL);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            pic.setImageBitmap(bitmap);
        }
    }

    public void setPic(ImageView pic) {
        this.pic = pic;
    }
}
