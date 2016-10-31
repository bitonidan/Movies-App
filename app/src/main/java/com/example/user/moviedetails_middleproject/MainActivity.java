package com.example.user.moviedetails_middleproject;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private AlertDialog dialog3, dialog1, dialog2, dialog4;
    private RelativeLayout layout;
    private SimpleCursorAdapter adapter;
    private MovieDBHelper helper;
    private CustomAdaper Movieadapter;
    private ListView listmovie;
    public long id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) findViewById(R.id.addbut);
        add.setOnClickListener(this);
        helper = new MovieDBHelper(this);
        layout = (RelativeLayout) findViewById(R.id.Layout);
        String[] from = {"title", "image"};
        int[] to = {R.id.titlemovie, R.id.postermovie};
        listmovie = ((ListView) findViewById(R.id.listMovie));
       // adapter = new SimpleCursorAdapter(this, R.layout.item_list, helper.getMoviesCursor(), from, to, 0);
        listmovie.setAdapter(Movieadapter);
       // listmovie.setAdapter(adapter);
        listmovie.setOnItemClickListener(this);
        listmovie.setOnItemLongClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_Exit:
                dialog3 = new AlertDialog.Builder(this).create();
                dialog3.setTitle("Exit From App:");
                dialog3.setMessage("Do You Want To Quit?");
                dialog3.setButton(BUTTON_NEGATIVE, "No", this);
                dialog3.setButton(BUTTON_POSITIVE, "Yes", this);
                dialog3.show();
                break;
            case R.id.menu_Dropall:
                dialog1 = new AlertDialog.Builder(this).create();
                dialog1.setButton(BUTTON_POSITIVE, "Yes", this);
                dialog1.setButton(BUTTON_NEGATIVE, "No", this);
                dialog1.setTitle("Delete Movies:");
                dialog1.setMessage("Are You Sure To Delete All Movies?");
                dialog1.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        dialog2 = new AlertDialog.Builder(this).create();
        dialog2.setTitle("Add Movie");
        dialog2.setMessage("Do You Want To Add A New Movie? ");
        dialog2.setButton(BUTTON_POSITIVE, "manual", this);
        dialog2.setButton(BUTTON_NEGATIVE, "Web", this);
        dialog2.show();

    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (dialog2 != null && dialog2.isShowing()) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent = new Intent(this, MovieAct.class);
                    intent.putExtra("flag", "add");
                    startActivity(intent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    Intent in = new Intent(this, WebSerchAct.class);
                    startActivity(in);
                    break;
            }

        } else {
            if (dialog3 != null && dialog3.isShowing()) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            } else {
                if (dialog1 != null && dialog1.isShowing()) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            helper.deleteallmovie();
                            onResume();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;

                    }
                } else {
                    if (dialog4 != null && dialog4.isShowing()) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                helper.deletemovie(id);
                                onResume();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }

                    }
                }
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Movieadapter = new CustomAdaper(this, helper.getMoviesCursor(), 0);
        listmovie.setAdapter(Movieadapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.id = id;
        Intent intent = new Intent(this, MovieAct.class);
        intent.putExtra("Movie_id", id);
        intent.putExtra("flag", "update");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        this.id = id;
        dialog4 = new AlertDialog.Builder(this).create();
        dialog4.setTitle("Delete Item");
        dialog4.setMessage("Are You Sure To Delete?");
        dialog4.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", this);
        dialog4.setButton(DialogInterface.BUTTON_NEGATIVE, "No", this);
        dialog4.show();
        return true;
    }



}














