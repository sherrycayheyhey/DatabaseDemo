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
            //create a table (id part will automatically add id number counts)
            //(for the pokemon example you would either want to add them all in order or better yet, make the number column the Primary key
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS pokemon (name VARCHAR, number INT(3), id INTEGER PRIMARY KEY)");

            //put info int the table (notice the single quotes!)
            //each time the app is run this will be added again so in a real app move to a loop or something
            myDatabase.execSQL("INSERT INTO pokemon (name, number) VALUES ('Bulbasaur', 001)");
            myDatabase.execSQL("INSERT INTO pokemon (name, number) VALUES ('Nosepass', 299)");

            //pull info out of the database
            //get only the OG/Kanto pokemon :) 
            Cursor c = myDatabase.rawQuery("SELECT * FROM pokemon WHERE number <= 151", null);
            //WHERE name = 'Nosepass' gets all pokemon named Nosepass
            //WHERE name = 'Nosepass' AND number = 124 gets all pokemon named Nosepass with number 23 (none in this case)
            //WHERE name LIKE 'S%' gets all pokemon that start with S
            //name LIKE '%a%' gets all pokemon that have anything before and anything after an a in their name
            //LIMIT 1 at the end limits the search to one result
            //DELETE FROM pokemon WHERE name = 'Bulbasaur' deletes all pokemon named Bulbasaur

            //get the index
            int nameIndex = c.getColumnIndex("name");
            int numberIndex = c.getColumnIndex("number");
            int idIndex = c.getColumnIndex("id");
            //put cursor to starting position of the table
            c.moveToFirst();

            //loop through each row of the table
            while (c != null) {
                Log.i("name", c.getString(nameIndex));
                Log.i("number", Integer.toString(c.getInt(numberIndex)));
                Log.i("id", Integer.toString(c.getInt(idIndex)));
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
