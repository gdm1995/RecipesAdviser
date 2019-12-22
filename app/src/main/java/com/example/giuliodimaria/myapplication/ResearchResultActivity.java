package com.example.giuliodimaria.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//SET THE FEEDBACK TO GET THE VISITED RECIPE
public class ResearchResultActivity extends AppCompatActivity
{
    public static final String COSTDELLARICETTASUGGERITA = "COSTDELLARICETTASUGGERITA";
    public static final String DIFFICOLTARICETTASUGGERITA = "DIFFICOLTARICETTASUGGERITA";
    public static final String PRESENTAZIONERICETTASUGGERITA = "PRESENTAZIONERICETTASUGGERITA";
    public static final String PREPARAZIONERICETTASUGGERITA = "PREPARAZIONERICETTASUGGERITA";
    public static final String TIMEDELLARICETTASUGGERITA = "TIMEDELLARICETTASUGGERITA";
    public static final String IDIMMAGINERICETTASUGGERITA = "IDIMMAGINERICETTASUGGERITA";
    public static final String NOMEDELLARICETTASUGGERITA = "NOMEDELLARICETTASUGGERITA";
    public static final String CATEGORYDELLARICETTASUGGERITA = "CATEGORYDELLARICETTASUGGERITA";
    public static final String USERNAMERACCOMANDATO = "USERNAMERACCOMANDATO";
    private RatingBar ratingBar;
    private String userId;
    private String nomeRicetta;
    private boolean votato;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_result);
        addListenerOnRatingBar();
        votato = false;
        Intent intent = getIntent();
        userId = intent.getExtras().getString(USERNAMERACCOMANDATO);
        TextView titleTextView = findViewById(R.id.titleTextView);
        nomeRicetta = intent.getExtras().getString(NOMEDELLARICETTASUGGERITA);
        titleTextView.setText(nomeRicetta);
        TextView categoryView = findViewById(R.id.categoryTextView);
        categoryView.setText(intent.getExtras().getString(CATEGORYDELLARICETTASUGGERITA));
        ImageView recipeImage = findViewById(R.id.recipeImage);
        recipeImage.setImageResource(intent.getExtras().getInt(IDIMMAGINERICETTASUGGERITA));
        recipeImage.setContentDescription(intent.getExtras().getString(NOMEDELLARICETTASUGGERITA));
        TextView tempoTxt = findViewById(R.id.tempoTxt);
        tempoTxt.setText(intent.getExtras().getString(TIMEDELLARICETTASUGGERITA));
        TextView difficoltaTxt = findViewById(R.id.difficoltàTxt);
        difficoltaTxt.setText(intent.getExtras().getString(DIFFICOLTARICETTASUGGERITA));
        TextView costTxt = findViewById(R.id.costTxt);
        costTxt.setText(intent.getExtras().getString(COSTDELLARICETTASUGGERITA));
        TextView presentationTxt = findViewById(R.id.presentationTxt);
        presentationTxt.setText(intent.getExtras().getString(PRESENTAZIONERICETTASUGGERITA));
        TextView preparation = findViewById(R.id.preparation);
        preparation.setText(intent.getExtras().getString(PREPARAZIONERICETTASUGGERITA));

        SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(ResearchResultActivity.this);
        try
        {
            SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query("FEEDBACKS", null, null, null, null, null, null);
            if (cursor.moveToFirst())
            {
                if (cursor.getString(1).equalsIgnoreCase(userId) && cursor.getString(2).equalsIgnoreCase(nomeRicetta))
                {
                    Toast.makeText(this, "Questa ricetta è stata già valutata in precedenza con un voto di " + cursor.getString(3), Toast.LENGTH_LONG).show();
                } else
                {
                    while (cursor.moveToNext())
                    {
                        if (cursor.getString(1).equalsIgnoreCase(nomeRicetta) && cursor.getString(2).equalsIgnoreCase(userId))
                        {
                            Toast.makeText(this, "Questa ricetta è stata già valutata in precedenza con un voto di " + cursor.getString(3), Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        } catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(ResearchResultActivity.this, "Rating già aggiunto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ResearchSettingsActivity.SHOW_RECIPE_RESULTED)
//        {
//            if (resultCode == RESULT_OK)
//            {
//
//            }
//        }
//    }

    public void addListenerOnRatingBar()
    {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                if (!votato)
                {
                    MediaPlayer mp;

                    SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(ResearchResultActivity.this);
                    try
                    {
                        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                        ContentValues insertionValues;
                        insertionValues = new ContentValues();
                        insertionValues.put("idUser", userId);
                        insertionValues.put("nameIdRecipe", nomeRicetta);
                        switch (String.valueOf(ratingBar.getRating()))
                        {
                            case "1.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_1_star);
                                mp.start();
                                insertionValues.put("seen", "1.0");
                                break;

                            case "2.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_2_stars);
                                mp.start();
                                insertionValues.put("seen", "2.0");
                                break;
                            case "3.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_3_stars);
                                mp.start();
                                insertionValues.put("seen", "3.0");
                                break;
                            case "4.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_4_stars);
                                mp.start();
                                insertionValues.put("seen", "4.0");
                                break;
                            default:
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_5_stars);
                                mp.start();
                                insertionValues.put("seen", "5.0");
                                break;
                        }
                        db.insert("FEEDBACKS", null, insertionValues);
                    } catch (SQLiteException e)
                    {
                        Toast toast = Toast.makeText(ResearchResultActivity.this, "Rating già aggiunto", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), "Rating aggiunto " + String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                    votato = true;
                } else
                {
                    MediaPlayer mp;
                    SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(ResearchResultActivity.this);
                    try
                    {
                        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                        ContentValues insertionValues;
                        insertionValues = new ContentValues();
                        insertionValues.put("idUser", userId);
                        insertionValues.put("nameIdRecipe", nomeRicetta);
                        switch (String.valueOf(ratingBar.getRating()))
                        {
                            case "1.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_1_star);
                                mp.start();
                                insertionValues.put("seen", "1.0");
                                break;

                            case "2.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_2_stars);
                                mp.start();
                                insertionValues.put("seen", "2.0");
                                break;
                            case "3.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_3_stars);
                                mp.start();
                                insertionValues.put("seen", "3.0");
                                break;
                            case "4.0":
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_4_stars);
                                mp.start();
                                insertionValues.put("seen", "4.0");
                                break;
                            default:
                                mp = MediaPlayer.create(ResearchResultActivity.this, R.raw.nosedive_5_stars);
                                mp.start();
                                insertionValues.put("seen", "5.0");
                                break;
                        }
                        db.insert("FEEDBACKS", null, insertionValues);
                    } catch (SQLiteException e)
                    {
                        Toast toast = Toast.makeText(ResearchResultActivity.this, "Rating già aggiunto", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), "Rating modificato " + String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 300);
                    toast.show();
                }
            }
        });
    }
}