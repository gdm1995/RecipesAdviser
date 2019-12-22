package com.example.giuliodimaria.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void proceedToSettings(View view)
    {
        EditText pass1 = findViewById(R.id.passwordEditText);
        EditText pass2 = findViewById(R.id.passwordRepeatEditText);
        String passTxt1 = pass1.getText().toString();
        String passTxt2 = pass2.getText().toString();

        if(passTxt1.equals(passTxt2))//Passwords match
        {
            //It was easier to specify the editText but using anonymous classes it's funnier
            //Checking if the username editText is not empty
            if(((EditText)(findViewById(R.id.usernameEditText))).getText().toString().trim().length() > 0)//No empty editText
            {
                //Creation of the sharedPreferenceUserId
                SharedPreferences sharedPref = getApplication().getSharedPreferences(MainActivity.USERNAMEFROMREGISTRATION, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(MainActivity.USERNAMEFROMREGISTRATION,((EditText)(findViewById(R.id.usernameEditText))).getText().toString());
                editor.putString(MainActivity.PASSWORDFROMREGISTRATION,((EditText)(findViewById(R.id.passwordEditText))).getText().toString());
                editor.apply();

                Intent intent = new Intent(this, IngredientsSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci un username", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 550);
                toast.show();
            }
        }

        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Le password non coincidono", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 550);
            toast.show();
        }

    }


}
