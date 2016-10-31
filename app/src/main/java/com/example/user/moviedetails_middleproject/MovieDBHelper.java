package com.example.user.moviedetails_middleproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;

/**
 * Created by User on 18/07/2016.
 */
public class MovieDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME="movies";
    public static final String COL_ID="_id";
    public static final String COL_TITLE="title";
    public static final String COL_PLOT="plot";
    public static final String COL_IMAGE="image";

    public MovieDBHelper(Context context) {
        super(context,"movies.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %1$s ( %2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT , %4$s TEXT, %5$s TEXT ) " ,
                TABLE_NAME , COL_ID, COL_TITLE, COL_PLOT, COL_IMAGE));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertmovie(Movie movie){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_TITLE,movie.getTitle());
        values.put(COL_PLOT,movie.getPlot());
        values.put(COL_IMAGE,movie.getImage());
        db.insert(TABLE_NAME,null,values);
    }

    public Cursor getMoviesCursor(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);

    }
    public Movie getImagebyId(long id){
        Movie movie=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE image="+ id , null);
        if (c.moveToNext()) {
            String title=c.getString(c.getColumnIndex(COL_TITLE));
            String plot= c.getString(c.getColumnIndex(COL_PLOT));
            String image= c.getString(c.getColumnIndex(COL_IMAGE));
            movie=new Movie((int)id,title,plot,image);
        }
        c.close();
        return movie;

    }

    public Movie getmoviebyId (long id){
        Movie movie=null;
        SQLiteDatabase db=getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE _id = "+ id, null);
        if (c.moveToNext()) {
            String title=c.getString(c.getColumnIndex(COL_TITLE));
            String plot= c.getString(c.getColumnIndex(COL_PLOT));
            String image= c.getString(c.getColumnIndex(COL_IMAGE));
            movie=new Movie((int)id,title,plot,image);
        }
        c.close();
        return movie;
    }

    public void deletemovie(long id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=" + id,null);
        db.close();
    }

    public void deleteallmovie (){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public void updateMovie(long id,Movie movie){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",movie.getTitle());
        values.put("plot",movie.getPlot());
        values.put("image",movie.getImage());
        db.update(TABLE_NAME,values,COL_ID+"="+id,null);
        db.close();

    }



}
