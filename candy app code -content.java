package edu.niu.cristianaguirre.contentmain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "candyDB",
                                    TABLE_NAME = "candyTable",
                                    ID = "id",
                                    NAME = "name",
                                    PRICE = "price";

        private static final int DATABASE_VERSION = 1;


        public DatabaseManager(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //string that contains the SQL statement to create the database
            String sqlCreate = " create table " + TABLE_NAME
                            +  "(" + ID + " integer primary key autoincrement, "
                            +  NAME + " text, "
                            +  PRICE + " real )";

        //create the database
        db.execSQL(sqlCreate);
        }//end on create

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //string that drops the old table if it exist
            String sqlDrop = "drop table if exists " + TABLE_NAME;

            //drop the old table
            db.execSQL(sqlDrop);

            //Re-create table
            onCreate(db);
        }//end onUpgrade


        public void insert( Candy candy)
        {
            //Get the database
            SQLiteDatabase database = getWritableDatabase();

            //string object to insert the data into the database
            String sqlInsert = "insert into " + TABLE_NAME
                             + " values ( null, '" + candy.getName() + "', "
                             + "'" + candy.getPrice() + "' )";

            //insert the data into the database
            database.execSQL(sqlInsert);

            //close the database
            database.close();
        }//end insert

        public ArrayList<Candy> selectAll()
        {
            //sting for getting the information from the database
            String sqlQuery = "select * from " + TABLE_NAME;

            //get the database with the information
            SQLiteDatabase database = getWritableDatabase();

            //create the cursor with all of the database information
            Cursor cursor = database.rawQuery(sqlQuery, null);

            //create the arraylist that will eventually be returned from the method
            ArrayList<Candy> candies = new ArrayList<>();

            //loop that will transfer the information from the cursor for array list
            while (cursor.moveToNext())
                {
                    Candy currentCandy = new Candy(Integer.parseInt(cursor.getString(0)),
                                                    cursor.getString(1),
                                                    cursor.getDouble(2));

                    //put the candy object into the arraylist
                    candies.add(currentCandy);
                }

                //close the database
                database.close();

                //return the array list
                return candies;
        }//end select all

        public void deleteById(int id)
        {
            //string with the Sql command
            String sqlDelete = "delete from " + TABLE_NAME + " where " + ID + " = " + id;

            //get the database
            SQLiteDatabase database = getWritableDatabase();

            //delete the candy from the database
            database.execSQL(sqlDelete);

            //close the database
            database.close();
        }//end delete by ID


        public void updateById( int currentID, String newName, double newPrice)
        {
            //get the database
            SQLiteDatabase database = getWritableDatabase();

            //string with the sql command
            String sqlUpdate = "update " + TABLE_NAME + " set " + NAME + " = '" + newName + "', "
                                                                + PRICE + " = '" + newPrice + "'"
                                                        + " where " + ID + " = " + currentID;

            //update the database
            database.execSQL(sqlUpdate);

            //close the database
            database.close();
        }//end updateById


        public Candy selectById ( int currentId )
        {
            //get the database
            SQLiteDatabase database = getWritableDatabase();

            //string with the sql command
            String sqlQuery = " select * from " + TABLE_NAME
                            + " where " + ID + " = " + currentId;

            //create a cursor to hold the retrieved information
            Cursor cursor = database.rawQuery(sqlQuery, null);

            //create the candy objects with the info
            Candy candy = null;
            if (cursor.moveToFirst())
            {
                candy = new Candy( Integer.parseInt(cursor.getString(0)),
                                    cursor.getString(1),
                                    cursor.getDouble(2));
            }

            return candy;
        }// select by ID


    }//end DatabaseManager
