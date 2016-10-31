package com.example.user.moviedetails_middleproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 31/07/2016.
 */
public class CustomAdaper extends CursorAdapter implements View.OnClickListener {
    private LayoutInflater inflator;
    private Context context;
    MovieDBHelper helper;
    private String text,posterurl;

    private ImageView postermovie2;
    private String postermovie;

    public CustomAdaper(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflator = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        helper = new MovieDBHelper(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.titlemovie);
        Button share = (Button) view.findViewById(R.id.butshare);
        ImageView postermovie2 = (ImageView) view.findViewById(R.id.postermovie);


        text = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_TITLE));
        postermovie = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_IMAGE));

       // ermovie2.setImageBitmap();
        textView.setText(text);
        share.setOnClickListener(this);

        GetPosterTask getlistpic = new GetPosterTask();
        getlistpic.setPic(postermovie2);
        getlistpic.execute(postermovie);



    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflator.inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void onClick(View v) {
       String sharebody=text+"\n\n"+postermovie;
       Intent sharingmovie=new Intent(Intent.ACTION_SEND);
       sharingmovie.setType("text/plain");
       sharingmovie.putExtra(Intent.EXTRA_SUBJECT,"A movie from My Movie App") ;
       sharingmovie.putExtra(Intent.EXTRA_TEXT,sharebody);
        context.startActivity(sharingmovie);
    }
}
