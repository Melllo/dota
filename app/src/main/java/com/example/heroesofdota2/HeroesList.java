package com.example.heroesofdota2;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesofdota2.adapter.ListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeroesList extends Activity {

    RecyclerView recyclerView;
    ListAdapter listAdapter;
    List<String> heroes;
    List<Integer> heroes_id;

    //database
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        recyclerView = findViewById(R.id.list);
        heroes_id = new ArrayList<Integer>();
        heroes = new ArrayList<String>();

        listAdapter = new ListAdapter(heroes_id,heroes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        //database
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDataToArrays();
    }

    public void setDataToArrays(){
        Cursor cursor1 = mDb.rawQuery("select id, name from heroes",null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()) {
            heroes.add(cursor1.getString(1));
            heroes_id.add(Integer.valueOf(cursor1.getString(0)));
            cursor1.moveToNext();
        }
        cursor1.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.close();
    }
}
