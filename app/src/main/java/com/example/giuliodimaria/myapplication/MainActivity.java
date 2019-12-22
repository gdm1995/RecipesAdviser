package com.example.giuliodimaria.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.strictmode.DiskReadViolation;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//When you got time :
// Implement progress bar while database is installing(it's needed to check if the database it's already installed.

public class MainActivity extends AppCompatActivity
{
    public static final String USERNAMEFROMREGISTRATION = "usernameFromRegistration";
    public static final String PASSWORDFROMREGISTRATION = "passwordFromRegistration";
    public static final String USERNAMEFROMHOME = "usernameFromHome";
    public static final String ARRAYRICETTEDB = "arrayRecipesFromDB";
    SQLiteOpenHelper myDatabaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper =new MyDatabaseHelper(this);
        db=myDatabaseHelper.getReadableDatabase();

    }

    public void Introduction(View view)
    {
        Intent intent = new Intent(this, IntroductionActivity.class);
        startActivity(intent);
    }

    public void proceedToSettingsForResults(View view)
    {
        //Check if in the database there is the name
        EditText editText = findViewById(R.id.userEditText);
        EditText password = findViewById(R.id.passwordMain);
        int esaminated = 1;
        if (editText.getText().toString().equals(""))
        {
            Toast toast = Toast.makeText(this, "Inserisci l'username", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
            toast.show();
        } else
        {
            SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(this);
            SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("USER", null, null, null, null, null, null);

            if (cursor.moveToFirst())
            {
                if ((cursor.getString(1).equalsIgnoreCase(editText.getText().toString())) && (cursor.getString(2).equals(password.getText().toString())))
                {
                    Intent intent = new Intent(this, ResearchSettingsActivity.class);
                    intent.putExtra(USERNAMEFROMHOME, editText.getText().toString());
                    startActivity(intent);
                } else
                {
                    while (cursor.moveToNext())
                    {
                        if ((cursor.getString(1).equalsIgnoreCase(editText.getText().toString())) && (cursor.getString(2).equals(password.getText().toString())))
                        {
                            Intent intent = new Intent(this, ResearchSettingsActivity.class);
                            intent.putExtra(USERNAMEFROMHOME, editText.getText().toString());
                            startActivity(intent);
                        }
                        else
                        {
                            esaminated++;
                        }
                    }
                    esaminated++;
                    if(esaminated>cursor.getCount())
                    {
                        Toast toast = Toast.makeText(this, "Username o password errati. Riprovare.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
                        toast.show();
                    }
                }
            } else
            {
                Toast toast = Toast.makeText(this, "Non ci sono ancora utenti registrati", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
                toast.show();
            }
            cursor.close();
            db.close();
        }

    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Sicuro di voler uscire?")
                .setMessage("Tornando indietro usciresti dall'app, sei sicuro?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        MainActivity.super.onBackPressed();//Usciamo realmente come se non avessimo fatto nulla
                    }
                }).create().show();
    }
}
