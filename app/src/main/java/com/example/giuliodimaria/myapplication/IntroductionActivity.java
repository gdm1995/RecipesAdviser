package com.example.giuliodimaria.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

public class IntroductionActivity extends AppCompatActivity
{
//Comment lines here and ResearchResultActivity to have API 15
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        TextView textView= findViewById(R.id.presentationText);
        textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
    }

    public void registration(View view)
    {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
