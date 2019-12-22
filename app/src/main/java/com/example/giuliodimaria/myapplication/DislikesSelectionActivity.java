package com.example.giuliodimaria.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class DislikesSelectionActivity extends AppCompatActivity
{
    private SQLiteDatabase db;
    private Cursor cursor;
    private static ArrayList<String> selectedDislikesNames;
    private final String RECUPEROELEMENTI = "CheckedElements";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dislikes_selection);

        if (savedInstanceState != null)
            selectedDislikesNames = savedInstanceState.getStringArrayList(RECUPEROELEMENTI);

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
            if (selectedDislikesNames == null)
                selectedDislikesNames = new ArrayList<>();
            CaptionCheckBoxAdapterDislikesSelectionActivity adapter = new CaptionCheckBoxAdapterDislikesSelectionActivity(ingredientsName, selectedDislikesNames);
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
    public void onDestroy()
    {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    //Terminates the setting section and send to the setting activity with a new task
    public void dislikes(View view)
    {
        SharedPreferences sharedPref = getApplication().getSharedPreferences(MainActivity.USERNAMEFROMREGISTRATION, Context.MODE_PRIVATE);
        String userId = sharedPref.getString(MainActivity.USERNAMEFROMREGISTRATION, "Failing passing the username from registration");
        String password = sharedPref.getString(MainActivity.PASSWORDFROMREGISTRATION, "Failing passing the password from registration");

        ArrayList<String> allergies = IngredientsSelectionActivity.getSelectedIngredientsNames();
        ArrayList<String> likes = LikesSelectionActivity.getSelectedIngredientsNames();
        ArrayList<String> dislikes = DislikesSelectionActivity.getSelectedIngredientsNames();

        insertInDBUsername(userId, password);
        insertInDB(allergies, 0, userId);
        insertInDB(likes, 1, userId);
        insertInDB(dislikes, 2, userId);

        Intent intent = new Intent(DislikesSelectionActivity.this, com.example.giuliodimaria.myapplication.ResearchSettingsActivity.class);
        intent.putExtra(MainActivity.USERNAMEFROMREGISTRATION, userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void insertInDBUsername(String userId, String password)
    {
        SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(this);
        try
        {
            SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
            ContentValues insertionValues = new ContentValues();
            insertionValues.put("idUser", userId);
            insertionValues.put("password", password);
            db.insert("USER", null, insertionValues);
        } catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database problem while inserting username", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //To insert elements in the DB
    private void insertInDB(ArrayList<String> values, int switcher, String userId)
    {
        String table;

        switch (switcher)
        {
            case 0:
                table = "ALLERGIES";
                break;
            case 1:
                table = "LIKES";
                break;
            default:
                table = "DISLIKES";
        }

        SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(this);
        try
        {
            SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
            for (int i = 0; i < values.size(); i++)
            {
                ContentValues insertionValues = new ContentValues();
                insertionValues.put("idUser", userId);
                insertionValues.put("nameIdIngredient", values.get(i));
                db.insert(table, null, insertionValues);
            }
        } catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database problem while inserting " + table.toLowerCase(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        bundle.putStringArrayList(RECUPEROELEMENTI, selectedDislikesNames);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(bundle);
    }

    public static void anElementHasBeenClicked(String elementoSelezionato)
    {
        selectedDislikesNames.add(elementoSelezionato);
    }

    public static void anElementHasBeenUnclicked(String elementoDeSelezionato)
    {
        selectedDislikesNames.remove(elementoDeSelezionato);
    }

    public static ArrayList<String> getSelectedIngredientsNames()
    {
        return DislikesSelectionActivity.selectedDislikesNames;
    }
}
