package com.example.giuliodimaria.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IngredientsSelectionActivity extends AppCompatActivity
{
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] ingredientsName;
    private static ArrayList<String> selectedIngredientsNames;
    private final String RECUPEROELEMENTI = "CheckedElements";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_selection);

        if (savedInstanceState != null)
            selectedIngredientsNames = savedInstanceState.getStringArrayList(RECUPEROELEMENTI);
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
            if (selectedIngredientsNames == null)
                selectedIngredientsNames = new ArrayList<>();
            CaptionCheckBoxAdapterIngredientsSelectionActivity adapter = new CaptionCheckBoxAdapterIngredientsSelectionActivity(ingredientsName, selectedIngredientsNames);
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
        bundle.putStringArrayList(RECUPEROELEMENTI, selectedIngredientsNames);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void ingredients(View view)
    {
        Intent intent = new Intent(this, LikesSelectionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Sicuro di voler uscire?")
                .setMessage("Avendo avviato una fase di registrazione, tornando indietro tornerai alla home dell'app e tutti i dati da " +
                        "te inseriti saranno perduti. Vuoi comunque procedere?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                        Intent intent = new Intent(IngredientsSelectionActivity.this, com.example.giuliodimaria.myapplication.MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).create().show();
    }

    public static void anElementHasBeenClicked(String elementoSelezionato)
    {
        selectedIngredientsNames.add(elementoSelezionato);
    }

    public static void anElementHasBeenUnclicked(String elementoDeSelezionato)
    {
        selectedIngredientsNames.remove(elementoDeSelezionato);
    }

    public static ArrayList<String> getSelectedIngredientsNames()
    {
        return IngredientsSelectionActivity.selectedIngredientsNames;
    }
}
