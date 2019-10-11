package com.chromsicle.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //opens or creates a database (so cool that it does both!)
            //name of the database, mode is about access (MODE_PRIVATE means only this app can access it)
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Pokemon", MODE_PRIVATE, null);
            //create a table
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS pokemon (name VARCHAR, number INT(3))");

            //put info int the table (notice the single quotes!)
            //each time the app is run this will be added again so in a real app move to a loop or something
            myDatabase.execSQL("INSERT INTO pokemon (name, number) VALUES ('Bulbasaur', 001)");
            myDatabase.execSQL("INSERT INTO pokemon (name, number) VALUES ('Nosepass', 299)");

            //pull info out of the database
            Cursor c = myDatabase.rawQuery("SELECT * FROM pokemon", null);
            //get the index
            int nameIndex = c.getColumnIndex("name");
            int numberIndex = c.getColumnIndex("number");
            //put cursor to starting position of the table
            c.moveToFirst();

            //loop through each row of the table
            while (c != null) {
                Log.i("name", c.getString(nameIndex));
                Log.i("number", Integer.toString(c.getInt(numberIndex)));
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
