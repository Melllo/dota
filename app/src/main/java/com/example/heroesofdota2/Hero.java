package com.example.heroesofdota2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

public class Hero extends Activity {

    static int id;
    ImageView heroPic;
    TextView name;
    TextView inta;
    TextView agi;
    TextView str;
    TextView attack;
    TextView speed;
    TextView defense;
    List<Integer> skills_pics;
    List<String> skills_descriptions;
    List<String> skills_name;
    TextView description;
    TextView skill;

    //database
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_layout);


        heroPic = findViewById(R.id.hero_pic);
        name = findViewById(R.id.name_hero);
        inta = findViewById(R.id.inta);
        agi = findViewById(R.id.agi);
        str = findViewById(R.id.str);
        attack = findViewById(R.id.attack);
        speed = findViewById(R.id.speed);
        defense = findViewById(R.id.defense);
        skills_descriptions = new ArrayList<String>();
        skills_pics = new ArrayList<Integer>();
        skills_name = new ArrayList<String>();
        description = findViewById(R.id.description);
        skill = findViewById(R.id.skill);
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
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDataToArrays();
        initPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void setDataToArrays(){
        final TypedArray heroes_pics = getResources().obtainTypedArray(R.array.heroes);
        Cursor cursor1 = mDb.rawQuery("select * from heroes where id="+id,null);
        Cursor cursor2 = mDb.rawQuery("select * from skills where hero_id="+id,null);
        cursor1.moveToFirst();
        cursor2.moveToFirst();
        heroPic.setBackground(heroes_pics.getDrawable(Integer.valueOf(cursor1.getString(9))));
        name.setText(cursor1.getString(1));
        inta.setText(cursor1.getString(3));
        agi.setText(cursor1.getString(4));
        str.setText(cursor1.getString(5));
        attack.setText(cursor1.getString(6));
        speed.setText(cursor1.getString(7));
        defense.setText(cursor1.getString(8));
        cursor1.close();
        while (!cursor2.isAfterLast()) {
            skills_name.add(cursor2.getString(2));
            skills_pics.add(Integer.valueOf(cursor2.getString(4)));
            skills_descriptions.add(cursor2.getString(3));
            cursor2.moveToNext();
        }
        cursor2.close();
    }

    int count =0;
    public void initPage(){

        final LinearLayout skills = findViewById(R.id.skills_window);
        final TypedArray skills_pic = getResources().obtainTypedArray(R.array.skills);
        for(Integer item:  skills_pics){
        final ImageView image = new ImageView(this);
        image.setBackground(skills_pic.getDrawable(item));
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(140, 140);
        image.setLayoutParams(imageViewLayoutParams);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            skill.setText(skills_name.get(count));
            description.setText(skills_descriptions.get(count));
            count++;
            }
        });
        skills.addView(image,0);
        }
    }
}
