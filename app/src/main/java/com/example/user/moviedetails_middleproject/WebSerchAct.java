package com.example.user.moviedetails_middleproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WebSerchAct extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ArrayAdapter<String> adapter;
    private EditText editsearch;
    private String searchvalue;
    String title,aid;
    private  ArrayList<String> ids;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_serch);
        editsearch = (EditText) findViewById(R.id.keyword);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        ListView listmovie = (ListView) findViewById(R.id.listMovieWeb);
        listmovie.setAdapter(adapter);
        listmovie.setOnItemClickListener(this);
        findViewById(R.id.butsearch).setOnClickListener(this);
        ids = new ArrayList();

    }

    @Override
    public void onClick(View v) {
        adapter.clear();
        ids.clear();
        Getsearch search = new Getsearch();
        searchvalue= editsearch.getText().toString().replace(" ","%20");
        if (searchvalue.isEmpty()) {
            Toast.makeText(WebSerchAct.this, "You Must To enter the Field ", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            search.execute("http://www.omdbapi.com/?s="+searchvalue);

        }
    }

    class Getsearch extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                con = (HttpURLConnection) url.openConnection();
                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String result = "", line;
                line = reader.readLine();
                while (line != null) {
                    result += line;
                    line = reader.readLine();
            }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        adapter.clear();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(WebSerchAct.this, "Erorr", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray arr = obj.getJSONArray("Search");
                    for (int i = 0; i < arr.length(); i++) {
                         obj = arr.getJSONObject(i);
                         title = obj.getString("Title");
                        adapter.add(title);
                         aid = obj.getString("imdbID");
                        ids.add(aid);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        String id = (String) ids.get(i);
        GetDetails getDetails=new GetDetails();
        getDetails.execute(id);

    }


    private class GetDetails extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String urlstring = "http://omdbapi.com/?i=" + params[0];
            HttpURLConnection con = null;
            BufferedReader reader = null;
            String line=null;

            try {
                URL url = new URL(urlstring);
                con = (HttpURLConnection) url.openConnection();
                if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                line = reader.readLine();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    con.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return line;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(WebSerchAct.this, "Erorr", Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    JSONObject obj = new JSONObject(result);
                    String title = obj.getString("Title");
                    String plot = obj.getString("Plot");
                    String image = obj.getString("Poster");
                    Movie movie=new Movie(title,plot,image);
                    Intent in=new Intent(WebSerchAct.this,MovieAct.class);
                    in.putExtra("DetMov",movie);
                    in.putExtra("flag","web");
                    startActivity(in);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }

    }

}





