package com.example.triviagame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor cursor = null;
    HeaderClass headerClass = new HeaderClass();

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }
        return  instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public void close(){
        if(db != null){
            this.db.close();
        }
    }
    public int totalQuestions(){
        cursor = db.rawQuery("SELECT * FROM "+ HeaderClass.TABLE_NAME,new String[]{});
        //cursor.moveToFirst();
        return cursor.getCount();

        //return Integer.parseInt(cursor.getString(0));
        /*int temp;
        while(cursor.moveToNext()){
            temp = Integer.parseInt(cursor.getString(0));
            //temp[1] = cursor.getString(1);
            //buffer.append(""+address+"");

        }
        return 1500;*/
    }

    public int getBackground(){
        cursor = db.rawQuery("SELECT * FROM SavedSettings",new String[]{});

        return cursor.getCount();
    }

    public String[] getAddress(int randomID){
        cursor = db.rawQuery("SELECT question,Option1, Option2, Option3, Option4, correct_answer, category  FROM "+ HeaderClass.TABLE_NAME +" WHERE ID = " + randomID,new String[]{});
        String[] temp = new String[]{"", "","","", "","",""};
        //StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            temp[0] = cursor.getString(0);
            temp[1] = cursor.getString(1);
            temp[2] = cursor.getString(2);
            temp[3] = cursor.getString(3);
            temp[4] = cursor.getString(4);
            temp[5] = cursor.getString(5);
            temp[6] = cursor.getString(6);
            //buffer.append(""+address+"");
        }

        return temp;
    }
}
