package com.example.giuliodimaria.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LikesSelectionActivity extends AppCompatActivity
{
    private static ArrayList<String> selectedLikesNames;
    private final String RECUPEROELEMENTI = "CheckedElements";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes_selection);

        if (savedInstanceState != null)
            selectedLikesNames = savedInstanceState.getStringArrayList(RECUPEROELEMENTI);

        //Reading the recipes from where we'll take the ingredients
        SQLiteOpenHelper sqLiteOpenHelper = new MyDatabaseHelper(this);
        try
        {
            db = sqLiteOpenHelper.getReadableDatabase();
            cursor = db.query("INGREDIENTS", null, null, null, null, null, null);
            cursor.moveToFirst();
            int k = 0;
            String[] temp = new String[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++)
            {
                String name = cursor.getString(1);
                if (!(Arrays.asList(temp).contains(name)))
                {
                    temp[k] = name;
                    k++;
                }
                cursor.moveToNext();
            }
            String[] ingredientsName = new String[k];
            for (int i = 0; i < k; i++)
            {
                ingredientsName[i] = temp[i];
            }
            Arrays.sort(ingredientsName);
            if (selectedLikesNames == null)
                selectedLikesNames = new ArrayList<>();
            CaptionCheckBoxAdapterLikesSelectionActivity adapter = new CaptionCheckBoxAdapterLikesSelectionActivity(ingredientsName, selectedLikesNames);
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
        } catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database problems", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        bundle.putStringArrayList(RECUPEROELEMENTI, selectedLikesNames);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void likes(View view)
    {
        Intent intent = new Intent(this, DislikesSelectionActivity.class);
        startActivity(intent);
    }

    public static void anElementHasBeenClicked(String elementoSelezionato)
    {
        selectedLikesNames.add(elementoSelezionato);
    }

    public static void anElementHasBeenUnclicked(String elementoDeSelezionato)
    {
        selectedLikesNames.remove(elementoDeSelezionato);
    }

    public static ArrayList<String> getSelectedIngredientsNames()
    {
        return LikesSelectionActivity.selectedLikesNames;
    }
}
