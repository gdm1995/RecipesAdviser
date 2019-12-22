package com.example.giuliodimaria.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResearchSettingsActivity extends AppCompatActivity
{
	String userid;

	SeekBar sbCost;
	SeekBar sbTime;
	SeekBar sbDiff;
	TextView costTxt;
	TextView timeTxt;
	TextView diffTxt;
	RadioGroup tipologyRadio;

	public static final int SHOW_RECIPE_RESULTED = 1;  // The request code

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_research_settings);

		//      Now I need to check if the user is a new one or a veteran
		//      To do faster I've built a method that doesn't fire the database

		Intent intent = getIntent();
		if (intent.hasExtra(MainActivity.USERNAMEFROMREGISTRATION))
		{
			userid = intent.getExtras().getString(MainActivity.USERNAMEFROMREGISTRATION);
		} else
		{
			userid = intent.getExtras().getString((MainActivity.USERNAMEFROMHOME));
		}

		//Saying hello with a toast just under "l'adiviser" textView

		//THERE WAS A PROBLEM ON RETRIEVING THE X AND Y OF THE TEXT VIEW,
		//SO I CHECKED ONLINE TO GET THEM WHEN THE VIEW IS ACTUALLY CREATED
		//LIKE THAT IT WORKS BUT I SHOULD BETTER UNDERSTAND THE ONGLOBALLAYOUTLISTENER
		final TextView adviserTxT = findViewById(R.id.adviserTextView);
		adviserTxT.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
		{
			@Override public void onGlobalLayout()
			{
				adviserTxT.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int[] coordinates = new int[2]; //created to get the x and y coordinates
				adviserTxT.getLocationInWindow(coordinates);
				Toast toast = Toast.makeText(getApplicationContext(), "Benvenuto " + userid, Toast.LENGTH_SHORT);

				//Editing dynamically the toast appearance
				View view = toast.getView();

				//Gets the actual oval background of the Toast then sets the colour filter
				//https://developer.android.com/reference/android/graphics/PorterDuff.Mode
				view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

				//Gets the TextView from the Toast if you want to edit the text
				TextView text = view.findViewById(android.R.id.message);
				//                text.setTextSize(16);
				//                text.setTextColor(YOUR_TEXT_COLOUR);

				toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, coordinates[1]);
				toast.show();
			}
		});

		CheckBox checkBoxType = findViewById(R.id.checkBoxType);
		checkBoxType.setChecked(true);
		CheckBox checkBoxCost = findViewById(R.id.checkBoxCost);
		checkBoxCost.setChecked(true);
		CheckBox checkBoxTime = findViewById(R.id.checkBoxTime);
		checkBoxTime.setChecked(true);
		CheckBox checkBoxDiff = findViewById(R.id.checkBoxDiff);
		checkBoxDiff.setChecked((true));

		//Setting the  cost seek bar progress
		sbCost = findViewById(R.id.sbCost);
		costTxt = findViewById(R.id.costoTextView);

		sbCost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				switch (progress)
				{
				case 0:
					costTxt.setText("Molto poco");
					break;
				case 1:
					costTxt.setText("Poco");
					break;
				case 2:
					costTxt.setText("Valore medio");
					break;
				case 3:
					costTxt.setText("Tanto");
					break;
				default:
					costTxt.setText("Davvero tanto");
					break;
				}
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar)
			{
				CheckBox checkBox = findViewById(R.id.checkBoxCost);
				checkBox.setChecked(false);
				int progress = seekBar.getProgress();
				switch (progress)
				{
				case 0:
					costTxt.setText("Molto poco");
					break;
				case 1:
					costTxt.setText("Poco");
					break;
				case 2:
					costTxt.setText("Valore medio");
					break;
				case 3:
					costTxt.setText("Tanto");
					break;
				default:
					costTxt.setText("Davvero tanto");
					break;
				}
			}

			@Override public void onStopTrackingTouch(SeekBar seekBar)
			{

			}
		});

		//Setting the time seek bar progress
		sbTime = findViewById(R.id.sbTime);
		timeTxt = findViewById(R.id.tempoTextView);

		sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				switch (progress)
				{
				case 0:
					timeTxt.setText("1 min");
					break;
				case 1:
					timeTxt.setText("2 min");
					break;
				case 2:
					timeTxt.setText("3 min");
					break;
				case 3:
					timeTxt.setText("4 min");
					break;
				case 4:
					timeTxt.setText("5 min");
					break;
				case 5:
					timeTxt.setText("6 min");
					break;
				case 6:
					timeTxt.setText("8 min");
					break;
				case 7:
					timeTxt.setText("10 min");
					break;
				case 8:
					timeTxt.setText("12 min");
					break;
				case 9:
					timeTxt.setText("15 min");
					break;
				case 10:
					timeTxt.setText("20 min");
					break;
				case 11:
					timeTxt.setText("25 min");
					break;
				case 12:
					timeTxt.setText("30 min");
					break;
				case 13:
					timeTxt.setText("35 min");
					break;
				case 14:
					timeTxt.setText("40 min");
					break;
				case 15:
					timeTxt.setText("45 min");
					break;
				case 16:
					timeTxt.setText("50 min");
					break;
				case 17:
					timeTxt.setText("55 min");
					break;
				case 18:
					timeTxt.setText("60 min");
					break;
				case 19:
					timeTxt.setText("65 min");
					break;
				case 20:
					timeTxt.setText("70 min");
					break;
				case 21:
					timeTxt.setText("75 min");
					break;
				case 22:
					timeTxt.setText("80 min");
					break;
				case 23:
					timeTxt.setText("85 min");
					break;
				case 24:
					timeTxt.setText("90 min");
					break;
				case 25:
					timeTxt.setText("100 min");
					break;
				case 26:
					timeTxt.setText("110 min");
					break;
				case 27:
					timeTxt.setText("120 min");
					break;
				case 28:
					timeTxt.setText("130 min");
					break;
				case 29:
					timeTxt.setText("140 min");
					break;
				case 30:
					timeTxt.setText("150 min");
					break;
				case 31:
					timeTxt.setText("155 min");
					break;
				case 32:
					timeTxt.setText("180 min");
					break;
				case 33:
					timeTxt.setText("190 min");
					break;
				case 34:
					timeTxt.setText("210 min");
					break;
				case 35:
					timeTxt.setText("240 min");
					break;
				default:
					timeTxt.setText("280 min");
					break;
				}
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar)
			{
				CheckBox checkBox = findViewById(R.id.checkBoxTime);
				checkBox.setChecked(false);
				int progress = seekBar.getProgress();
				switch (progress)
				{
				case 0:
					timeTxt.setText("1 min");
					break;
				case 1:
					timeTxt.setText("2 min");
					break;
				case 2:
					timeTxt.setText("3 min");
					break;
				case 3:
					timeTxt.setText("4 min");
					break;
				case 4:
					timeTxt.setText("5 min");
					break;
				case 5:
					timeTxt.setText("6 min");
					break;
				case 6:
					timeTxt.setText("8 min");
					break;
				case 7:
					timeTxt.setText("10 min");
					break;
				case 8:
					timeTxt.setText("12 min");
					break;
				case 9:
					timeTxt.setText("15 min");
					break;
				case 10:
					timeTxt.setText("20 min");
					break;
				case 11:
					timeTxt.setText("25 min");
					break;
				case 12:
					timeTxt.setText("30 min");
					break;
				case 13:
					timeTxt.setText("35 min");
					break;
				case 14:
					timeTxt.setText("40 min");
					break;
				case 15:
					timeTxt.setText("45 min");
					break;
				case 16:
					timeTxt.setText("50 min");
					break;
				case 17:
					timeTxt.setText("55 min");
					break;
				case 18:
					timeTxt.setText("60 min");
					break;
				case 19:
					timeTxt.setText("65 min");
					break;
				case 20:
					timeTxt.setText("70 min");
					break;
				case 21:
					timeTxt.setText("75 min");
					break;
				case 22:
					timeTxt.setText("80 min");
					break;
				case 23:
					timeTxt.setText("85 min");
					break;
				case 24:
					timeTxt.setText("90 min");
					break;
				case 25:
					timeTxt.setText("100 min");
					break;
				case 26:
					timeTxt.setText("110 min");
					break;
				case 27:
					timeTxt.setText("120 min");
					break;
				case 28:
					timeTxt.setText("130 min");
					break;
				case 29:
					timeTxt.setText("140 min");
					break;
				case 30:
					timeTxt.setText("150 min");
					break;
				case 31:
					timeTxt.setText("155 min");
					break;
				case 32:
					timeTxt.setText("180 min");
					break;
				case 33:
					timeTxt.setText("190 min");
					break;
				case 34:
					timeTxt.setText("210 min");
					break;
				case 35:
					timeTxt.setText("240 min");
					break;
				default:
					timeTxt.setText("280 min");
					break;
				}
			}

			@Override public void onStopTrackingTouch(SeekBar seekBar)
			{
			}
		});

		//Setting the difficulty seek bar progress
		sbDiff = findViewById(R.id.sbDifficulty);
		diffTxt = findViewById(R.id.difficoltaTextView);

		sbDiff.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				switch (progress)
				{
				case 0:
					diffTxt.setText("Molto basso");
					break;
				case 1:
					diffTxt.setText("Basso");

					break;
				case 2:
					diffTxt.setText("Valore medio");

					break;
				case 3:
					diffTxt.setText("Elevato");

					break;
				default:
					diffTxt.setText("Davvero elevato");

					break;
				}
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar)
			{
				CheckBox checkBox = findViewById(R.id.checkBoxDiff);
				checkBox.setChecked(false);
			}

			@Override public void onStopTrackingTouch(SeekBar seekBar)
			{
				int progress = seekBar.getProgress();
				switch (progress)
				{
				case 0:
					diffTxt.setText("Molto basso");
					break;
				case 1:
					diffTxt.setText("Basso");

					break;
				case 2:
					diffTxt.setText("Valore medio");

					break;
				case 3:
					diffTxt.setText("Elevato");

					break;
				default:
					diffTxt.setText("Davvero elevato");

					break;
				}
			}
		});
	}

	public void costDoesntMatter(View view)
	{
		CheckBox checkBox = findViewById(R.id.checkBoxCost);
		boolean checked = checkBox.isChecked();
		if (checked)
		{
			costTxt.setText("Non importa");
		} else
		{
			int progress = sbCost.getProgress();
			String toWrite;
			switch (progress)
			{
			case 0:
				toWrite = "Molto poco";
				break;
			case 1:
				toWrite = "Poco";
				break;
			case 2:
				toWrite = "Valore medio";
				break;
			case 3:
				toWrite = "Tanto";
				break;
			default:
				toWrite = "Davvero tanto";
				break;
			}
			costTxt.setText(toWrite);
		}

	}

	public void timeDoesntMatter(View view)
	{
		CheckBox checkBox = findViewById(R.id.checkBoxTime);
		boolean checked = checkBox.isChecked();
		if (checked)
		{
			timeTxt.setText("Non importa");
		} else
		{
			int progress = sbTime.getProgress();
			String toWrite;
			switch (progress)
			{
			case 0:
				toWrite = "1 min";
				break;
			case 1:
				toWrite = "2 min";
				break;
			case 2:
				toWrite = "3 min";
				break;
			case 3:
				toWrite = "4 min";
				break;
			case 4:
				toWrite = "5 min";
				break;
			case 5:
				toWrite = "6 min";
				break;
			case 6:
				toWrite = "8 min";
				break;
			case 7:
				toWrite = "10 min";
				break;
			case 8:
				toWrite = "12 min";
				break;
			case 9:
				toWrite = "15 min";
				break;
			case 10:
				toWrite = "20 min";
				break;
			case 11:
				toWrite = "25 min";
				break;
			case 12:
				toWrite = "30 min";
				break;
			case 13:
				toWrite = "35 min";
				break;
			case 14:
				toWrite = "40 min";
				break;
			case 15:
				toWrite = "45 min";
				break;
			case 16:
				toWrite = "50 min";
				break;
			case 17:
				toWrite = "55 min";
				break;
			case 18:
				toWrite = "60 min";
				break;
			case 19:
				toWrite = "65 min";
				break;
			case 20:
				toWrite = "70 min";
				break;
			case 21:
				toWrite = "75 min";
				break;
			case 22:
				toWrite = "80 min";
				break;
			case 23:
				toWrite = "85 min";
				break;
			case 24:
				toWrite = "90 min";
				break;
			case 25:
				toWrite = "100 min";
				break;
			case 26:
				toWrite = "110 min";
				break;
			case 27:
				toWrite = "120 min";
				break;
			case 28:
				toWrite = "130 min";
				break;
			case 29:
				toWrite = "140 min";
				break;
			case 30:
				toWrite = "150 min";
				break;
			case 31:
				toWrite = "155 min";
				break;
			case 32:
				toWrite = "180 min";
				break;
			case 33:
				toWrite = "190 min";
				break;
			case 34:
				toWrite = "210 min";
				break;
			case 35:
				toWrite = "240 min";
				break;
			default:
				toWrite = "280 min";
			}
			timeTxt.setText(toWrite);
		}
	}

	public void suggestion(View view)
	{
		//Getting all settings

		String costSet = ((TextView) (findViewById(R.id.costoTextView))).getText().toString();
		String timeSet = ((TextView) (findViewById(R.id.tempoTextView))).getText().toString();
		String difficultySet = ((TextView) (findViewById(R.id.difficoltaTextView))).getText().toString();
		String categorySet = ((TextView) (findViewById(R.id.tipologyTextView))).getText().toString();
		ArrayList<String> liked = new ArrayList<>();
		ArrayList<String> disliked = new ArrayList<>();
		ArrayList<String> allergic = new ArrayList<>();
		ArrayList<FeedBacks> visited = new ArrayList<>();
		ArrayList<Recipe> ricette;
		//        ArrayList<Ingredient> ingredienti;

		SQLiteOpenHelper myDatabaseHelper = new MyDatabaseHelper(this);
		SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();

		//        Cursor cursor1 = db.query("FEEDBACKS", null, null, null, null, null, null);
		//        db.delete("FEEDBACKS",null,null);

		//Retrieving the allergies
		Cursor cursor = db.query("ALLERGIES", null, null, null, null, null, null);
		try
		{
			if (cursor.moveToFirst())
			{
				allergic.add(cursor.getString(2));
				while (cursor.moveToNext())
				{
					allergic.add(cursor.getString(2));
				}
			}
		} catch (SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Database problems", Toast.LENGTH_LONG);
			toast.show();
		}

		//Retrieving the feedbacks to know which recipe has been visited. Seen 0 false from 1 to 5 true
		cursor = db.query("FEEDBACKS", null, null, null, null, null, null);
		try
		{
			if (cursor.moveToFirst())
			{
				visited.add(new FeedBacks(cursor.getString(2), cursor.getString(1), cursor.getString(3)));
				while (cursor.moveToNext())
				{
					visited.add(new FeedBacks(cursor.getString(2), cursor.getString(1), cursor.getString(3)));
				}
			}
		} catch (SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Database problems", Toast.LENGTH_LONG);
			toast.show();
		}

		//Retrieving the liked
		cursor = db.query("LIKES", null, null, null, null, null, null);
		try
		{
			if (cursor.moveToFirst())
			{
				liked.add(cursor.getString(2));
				while (cursor.moveToNext())
				{
					liked.add(cursor.getString(2));
				}
			}
		} catch (SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Database problems", Toast.LENGTH_LONG);
			toast.show();
		}

		//Retrieving the disliked
		cursor = db.query("DISLIKES", null, null, null, null, null, null);
		try
		{
			if (cursor.moveToFirst())
			{
				disliked.add(cursor.getString(2));
				while (cursor.moveToNext())
				{
					disliked.add(cursor.getString(2));
				}
			}
		} catch (SQLiteException e)
		{
			Toast toast = Toast.makeText(this, "Database problems", Toast.LENGTH_LONG);
			toast.show();
		}

		//        Retrieving the recipes
		//        Instead of using the database I'll use the sharedPreferences folder with the aid of Json
		SharedPreferences sharedPref = getApplication().getSharedPreferences(MainActivity.ARRAYRICETTEDB, Context.MODE_PRIVATE);
		Gson gson = new Gson();
		String json = sharedPref.getString(MainActivity.ARRAYRICETTEDB, "Failing passing the username from registration");
		Type type = new TypeToken<List<Recipe>>()
		{
		}.getType();
		ricette = gson.fromJson(json, type);

		//        cursor = db.query("RECIPE", null, null, null, null, null, null);
		//        Cursor cursor1;
		//        try
		//        {
		//            if (cursor.moveToFirst())
		//            {
		//                while (cursor.moveToNext())
		//                {
		//                    ingredienti = new ArrayList<>();
		//                    String title, score, time, category, difficulty, presentation, preparation, price;
		//                    title = cursor.getString(1);
		//                    score = cursor.getString(2);
		//                    time = cursor.getString(3);
		//                    category = cursor.getString(4);
		//                    difficulty = cursor.getString(5);
		//                    presentation = cursor.getString(6);
		//                    preparation = cursor.getString(7);
		//                    price = cursor.getString(8);
		//                    cursor1 = db.query("INGREDIENTS", null, "nameIdRecipe=?", new String[]{title}, null, null, null);
		//
		//                    while (cursor1.moveToNext())
		//                    {
		//                        String nameIdIngredient, quantita, nameIdRecipe;
		//                        nameIdIngredient = cursor1.getString(1);
		//                        quantita = cursor1.getString(2);
		//                        nameIdRecipe = cursor1.getString(3);
		//                        ingredienti.add(new Ingredient(nameIdIngredient, quantita, nameIdRecipe));
		//                    }
		//
		//                    ricette.add(new Recipe(title, score, time, category, difficulty, presentation, ingredienti, preparation, price));
		//                }
		//            }
		//        } catch (SQLiteException e)
		//        {
		//            Toast toast = Toast.makeText(this, "Database problems", Toast.LENGTH_LONG);
		//            toast.show();
		//        }

		//Let's start to delete the recipes which don't match the selectors

		//Time selector
		if (!(timeSet.equals("Non importa")))
		{
			timeSet = timeSet.replace("min", "");
			timeSet = timeSet.trim();
			int timeSetConverted = Integer.parseInt(timeSet);
			String timeRecipe;
			int timeRecipeConverted;
			for (int i = 0; i < ricette.size(); i++)
			{
				timeRecipe = ricette.get(i).getTime();
				timeRecipe = timeRecipe.replace("min", "");
				timeRecipe = timeRecipe.trim();
				timeRecipeConverted = Integer.parseInt(timeRecipe);
				if (timeRecipeConverted > timeSetConverted)
				{
					ricette.remove(i);
					i--;
				}
			}
		}

		//Cost selector
		int valueCostSet, valueCostRecipe;
		switch (costSet)
		{
		case "Molto poco":
			valueCostSet = 0;
			break;
		case "Poco":
			valueCostSet = 1;
			break;
		case "Tanto":
			valueCostSet = 3;
			break;
		case "Davvero tanto":
			valueCostSet = 4;
			break;
		default:
			valueCostSet = 2;
		}
		if (!(costSet.equals("Non importa")))
		{
			for (int i = 0; i < ricette.size(); i++)
			{
				switch (ricette.get(i).getPrice())
				{
				case "molto basso":
					valueCostRecipe = 0;
					break;
				case "basso":
					valueCostRecipe = 1;
					break;
				case "elevato":
					valueCostRecipe = 3;
					break;
				case "molto elevata":
					valueCostRecipe = 4;
					break;
				default:
					valueCostRecipe = 2;
				}
				if (valueCostRecipe > valueCostSet)
				{
					ricette.remove(i);
					i--;
				}
			}
		}

		//Difficulty selector
		int valueDfficultySet, valueDiffRecipe;
		switch (difficultySet)
		{
		case "Molto basso":
			valueDfficultySet = 0;
			break;
		case "Basso":
			valueDfficultySet = 1;
			break;
		case "Valore medio":
			valueDfficultySet = 2;
			break;
		case "Elevato":
			valueDfficultySet = 3;
			break;
		default:
			valueDfficultySet = 4;
		}
		if (!(difficultySet.equals("Non importa")))
		{
			for (int i = 0; i < ricette.size(); i++)
			{
				switch (ricette.get(i).getDifficulty())
				{
				case "molto bassa":
					valueDiffRecipe = 0;
					break;
				case "bassa":
					valueDiffRecipe = 1;
					break;
				case "elevata":
					valueDiffRecipe = 3;
					break;
				case "molto elevata":
					valueDiffRecipe = 4;
					break;
				default:
					valueDiffRecipe = 2;
				}
				if (valueDiffRecipe > valueDfficultySet)
				{
					ricette.remove(i);
					i--;
				}
			}
		}

		//Category selector
		if (!(categorySet.equals("Non importa")))
		{
			for (int i = 0; i < ricette.size(); i++)
			{
				if (!(ricette.get(i).getCategory().equals(categorySet)))
				{
					ricette.remove(i);
					i--;
				}
			}
		}

		//Allergies selector
		if (allergic.size() > 0)
		{
			for (int i = 0; i < ricette.size(); i++) //For every lasting recipe
			{
				try
				{
					for (int j = 0; j < ricette.get(i).getIngredients().size(); j++) //For every ingredient in the i Recipe
					{
						for (int k = 0; k < allergic.size(); k++)//For every allergy
						{
							if (ricette.size() > 0)//'cause the remove can create an indexOutOfBoundException
							{
								if (ricette.get(i).getIngredients().get(j).getNameIng().equalsIgnoreCase(allergic.get(k)))//Check in the toString if there is the ingredient
								{
									k = allergic.size();
									j = ricette.get(i).getIngredients().size();
									ricette.remove(i);//If you find it, delete the recipe
									i--;
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					Toast.makeText(this, "" + i + ricette.size(), Toast.LENGTH_LONG);
				}
			}
		}

		//Already seen selector
		if (visited.size() > 0)
		{
			for (int i = 0; i < ricette.size(); i++) //For every lasting recipe
			{
				for (int j = 0; j < visited.size(); j++) //For every visited recipe (Feedback class is used here)
				{
					if (userid.equalsIgnoreCase(visited.get(j).getUsernameId()))
					{
						if (ricette.get(i).getTitle().equalsIgnoreCase(visited.get(j).getNameIdRecipe()))//Check in there the title
						{
							j = visited.size();//We have found the matching recipe
							ricette.remove(i);//So delete the already seen recipe
							i--;
						}
					}
				}
			}

		}

		//Filter likes
		if (!(liked.isEmpty()))
		{
			for (int i = 0; i < ricette.size(); i++) //For every lasting recipe
			{
				double sizeOfRecipe = ricette.get(i).getIngredients().size(); //Take the total number of ingredients
				String givenScoreStringVersion = ricette.get(i).getScore().replace(",", ".");
				double givenScore = Double.parseDouble(givenScoreStringVersion); //Take the actual given score
				double pointsPerIngredient = 5 / sizeOfRecipe; //Normalization of every single match increment

				for (int j = 0; j < liked.size(); j++) //For every liked string (containing the ingredientName)
				{
					if (ricette.get(i).getIngredients().toString().contains(liked.get(j)))//Check in the toString if there is the ingredient
					{
						givenScore += pointsPerIngredient;//Increment for every ingredient liked
					}
				}
				DecimalFormat df2 = new DecimalFormat("#.0");// Creating a double format with one decimal
				ricette.get(i).setScore(df2.format(givenScore));//Set the new score for this evaluation
			}
		}

		//Filter dislikes
		if (!(disliked.isEmpty()))
		{
			for (int i = 0; i < ricette.size(); i++) //For every lasting recipe
			{
				double sizeOfRecipe = ricette.get(i).getIngredients().size(); //Take the total number of ingredients
				double givenScore = Double.parseDouble(ricette.get(i).getScore().replace(",", ".")); //Take the actual given score
				double pointsPerIngredient = 7.5 / sizeOfRecipe; //Normalization of every single match decrement

				for (int j = 0; j < disliked.size(); j++) //For every disliked string (containing the ingredientName)
				{
					if (ricette.get(i).getIngredients().toString().contains(disliked.get(j)))//Check in the toString if there is the ingredient
					{
						givenScore -= pointsPerIngredient;//Decrement for every ingredient disliked
					}
				}
				if (givenScore < 0)
					givenScore = 0;
				DecimalFormat df2 = new DecimalFormat("#.0");// Creating a double format with one decimal
				ricette.get(i).setScore(df2.format(givenScore));//Set the new score for this evaluation
			}
		}

		//Collaboration with other users (realiabilty given thanks to Bravais-Pearson index up to 10 points
		ArrayList<FeedBacks> myUserFeedbacks = new ArrayList<>();
		Reliability myUser = new Reliability(myUserFeedbacks, 0);
		ArrayList<Reliability> users = new ArrayList<>(); //all the others
		for (int i = 0; i < visited.size(); i++) //Getting all feedbacks for my user and the others
		{
			String tempUsername = visited.get(i).getUsernameId();
			Reliability otherUser = new Reliability(new ArrayList<FeedBacks>(), 0);
			while (i < visited.size() && tempUsername.equalsIgnoreCase(visited.get(i).getUsernameId()))
			{
				if (tempUsername.equalsIgnoreCase(userid))
				{
					myUser.addFeedback(visited.get(i));
				} else
				{
					otherUser.addFeedback(visited.get(i));
				}
				i++;
			}
			users.add(otherUser);
		}
		if (myUser.getScores().size() > 0)//If our user has set no feedbacks it's hard to correlate him with anybody ahah
		{//But if he does, we don't want him to correlate only with people that tried his same things
			for (int i = 0; i < myUser.getScores().size(); i++)//For every feedback left by my user
			{
				for (int j = 0; j < users.size(); j++)//Let's take all the other users...
				{
					if (!(myUser.getScores().size() > users.get(j).getScores().size()))//We don't want users less expert than us
					{
						for (int k = 0; k < users.get(j).getScores().size(); k++)//...with their feedbacks
						{
							if (!(myUser.getScores().get(i).getNameIdRecipe().equalsIgnoreCase(users.get(j).getScores().get(k).getNameIdRecipe())))//And if their feedbacks don't match ours
							{
								users.get(j).getScores().remove(k);//Remove that recipe that we don't know yet
								k--;//To don't skip the element that will take the place of this one
							}
						}
					} else //So we now more than them, it's better to delete this useless user
					{
						users.remove(j);
						j--;
					}
				}
			}

			//Let's discover the reliability of each user
			if (myUser.getScores().size() > 5)
			{
				double[] x = new double[myUser.getScores().size()];
				for (int i = 0; i < myUser.getScores().size(); i++)
				{
					x[i] = Double.parseDouble(myUser.getScores().get(i).getSeen().replace(",", "."));
				}
				for (int i = 0; i < users.size(); i++)//For each user
				{
					double[] y = new double[users.get(i).getScores().size()];
					for (int j = 0; j < myUser.getScores().size(); j++)
					{
						y[j] = Double.parseDouble(users.get(i).getScores().get(i).getSeen().replace(",", "."));
					}
					double correlation = Math.abs(new org.apache.commons.math3.stat.correlation.PearsonsCorrelation().correlation(x, y));
					DecimalFormat df2 = new DecimalFormat("#.0");
					//we are taking the absolute value because even in strong negative correlation we find good suggestions
					//                ricette.get(i).setScore(df2.format(givenScore));
					users.get(i).setReliability(Double.parseDouble(df2.format(correlation)));
				}

				//Now that we have all correlations, let's take the highest score(and discover our bff in suggestions)
				Reliability max = null;
				for (int i = 0; i < users.size(); i++)//we have to esaminate all users
				{
					if (max == null)
					{
						max = users.get(i);
					} else
					{
						if (max.getReliability() < users.get(i).getReliability())
						{
							max = users.get(i);
						}
					}
				}

				//If there were no users enough good here max is null
				if (max != null)
				{
					//Let's give back to max his evaluations
					ArrayList<FeedBacks> maxSScores = new ArrayList<>();
					for (int i = 0; i < visited.size(); i++)
					{
						if (visited.get(i).getUsernameId().equalsIgnoreCase(max.getScores().get(0).getUsernameId()))//let's check his feedbacks
						{
							maxSScores.add(visited.get(i));
						}
					}

					//now we just have to take his best suggestions among the ones we already have and take the best of everything
					max.setScores(maxSScores);//Here you are, buddy :)

					for (int i = 0; i < max.getScores().size(); i++)//For each element evaluated by the user we selected as bff...
					{
						for (int j = 0; j < ricette.size(); j++)//Let's see all lasting recipes if fits with max ones
						{
							if (max.getScores().get(i).getNameIdRecipe().equalsIgnoreCase(ricette.get(j).getTitle()))//if they match let's revalue the score
							{
								ricette.get(j).setScore(String.valueOf(
										Double.parseDouble(ricette.get(j).getScore()) * (max.getReliability() * (Double.parseDouble(max.getScores().get(i).getSeen().replace(",", "."))) * 2)));
							}
						}
					}
				}
			}

		}

		//Getting the result
		if (ricette.size() > 0)
		{
			//The result
			double max = Double.parseDouble(ricette.get(0).getScore().replace(",", "."));
			Recipe winner;
			int indexWinner = 0;
			for (int i = 1; i < ricette.size(); i++)
			{
				if (Double.parseDouble(ricette.get(i).getScore().replace(",", ".")) > max)
				{
					max = Double.parseDouble(ricette.get(i).getScore().replace(",", "."));
					ricette.remove(indexWinner);
					indexWinner = i - 1;
					i--;
				} else
				{
					ricette.remove(i);
					i--;
				}
			}
			winner = ricette.get(indexWinner);
			Intent intent = new Intent(this, ResearchResultActivity.class);
			String winnerPic = setGoodFormat(winner.getTitle());
			//Setting the picture
			int id = getResources().getIdentifier(winnerPic, "drawable", getPackageName());
			intent.putExtra(ResearchResultActivity.IDIMMAGINERICETTASUGGERITA, id);
			//Setting the userId
			intent.putExtra(ResearchResultActivity.USERNAMERACCOMANDATO, userid);
			//Setting the title
			intent.putExtra(ResearchResultActivity.NOMEDELLARICETTASUGGERITA, winner.getTitle());
			//Setting the time
			intent.putExtra(ResearchResultActivity.TIMEDELLARICETTASUGGERITA, winner.getTime());
			//Setting the cost
			intent.putExtra(ResearchResultActivity.COSTDELLARICETTASUGGERITA, winner.getPrice());
			//Setting the category
			intent.putExtra(ResearchResultActivity.CATEGORYDELLARICETTASUGGERITA, winner.getCategory());
			//Setting the difficulty
			intent.putExtra(ResearchResultActivity.DIFFICOLTARICETTASUGGERITA, winner.getDifficulty());
			//Setting presentation
			intent.putExtra(ResearchResultActivity.PRESENTAZIONERICETTASUGGERITA, winner.getPresentation());
			//Setting the preparation
			intent.putExtra(ResearchResultActivity.PREPARAZIONERICETTASUGGERITA, winner.getPreparation());

			startActivityForResult(intent, SHOW_RECIPE_RESULTED);

		} else
		{
			MediaPlayer mp = MediaPlayer.create(this, R.raw.fail_trombone_01);
			mp.start();
			new AlertDialog.Builder(this).setTitle("Sono molto dispiaciuto").setMessage(
					("Ho analizzato tutti gli elementi di cui sono a conoscenza, " + "ma non esistono nel mio database ricette che possano soddisfare i tuoi criteri."
							+ "Prova ad inserire dei settaggi diversi.")).setNegativeButton("", null).setPositiveButton("OK, cambierò i miei settaggi.", null).create().show();
		}
	}

	private String setGoodFormat(String title)
	{
		int i = 0;
		title = title.toLowerCase();
		int valChar;
		String resultValue = "";
		while (title.length() > i)
		{
			if (title.charAt(i) != ' ')
			{
				valChar = title.charAt(i);
				if (((valChar >= 97) && (valChar <= 122)) || ((valChar >= 48) && (valChar <= 57)))
					resultValue += title.charAt(i);
				else
					resultValue += "";
			} else
			{
				resultValue += "_";
			}
			i++;
		}
		return resultValue;
	}

	public void difficultyDoesntMatter(View view)
	{
		CheckBox checkBox = findViewById(R.id.checkBoxDiff);
		boolean checked = checkBox.isChecked();
		if (checked)
		{
			diffTxt.setText("Non importa");
		} else
		{
			int progress = sbDiff.getProgress();
			String toWrite;
			switch (progress)
			{
			case 0:
				toWrite = "Molto basso";
				break;
			case 1:
				toWrite = "Basso";
				break;
			case 2:
				toWrite = "Valore medio";
				break;
			case 3:
				toWrite = "Elevato";
				break;
			default:
				toWrite = "Davvero elevato";
				break;
			}
			diffTxt.setText(toWrite);
		}
	}

	/*
		Onclick dei radioButtons, it works only when the elements get turned on. So all we have to do is to
		retrieve the typology selected,uncheck the checkbox and save the id of the selected element
	 */
	public void tipologyDoesntMatterRadios(View view)
	{
		//Getting the group radio button
		tipologyRadio = findViewById(R.id.radioGroup);
		int id = tipologyRadio.getCheckedRadioButtonId();
		CheckBox checkBoxType = findViewById(R.id.checkBoxType);
		checkBoxType.setChecked(false);
		RadioButton radioButton = findViewById(id);
		TextView textView = findViewById(R.id.tipologyTextView);
		textView.setText(radioButton.getText());
	}

	/*
		onClick del checkBox dei radioButton, if checked, it has to turn off the elements, otherwise if get unchecked
		recheck the radio button that was checked before, if there wasn't check the first one
	 */
	public void tipologyDoesntMatterCheckBox(View view)
	{
		tipologyRadio = findViewById(R.id.radioGroup);
		int id = tipologyRadio.getCheckedRadioButtonId();
		CheckBox checkBoxType = findViewById(R.id.checkBoxType);
		boolean checked = checkBoxType.isChecked();
		if (checked)
		{
			if (id != -1)
			{
				RadioButton radioButton = findViewById(id);
				radioButton.setChecked(false);
				TextView textView = findViewById(R.id.tipologyTextView);
				textView.setText("Non importa");
			}
		} else
		{
			if (id != -1)
			{
				//We uncheck the checkBox and reCheck the radioButton that was checked before
				RadioButton radioButton = findViewById(id);
				radioButton.setChecked(true); //I don't knwo why it doesn't want to work
				TextView textView = findViewById(R.id.tipologyTextView);
				textView.setText(radioButton.getText());
			} else
			{
				//There were no radioButtons checked before as it's the first time so I check the first one
				RadioButton radioButton = findViewById(R.id.radio_Antipasti);
				radioButton.setChecked(true);
				TextView textView = findViewById(R.id.tipologyTextView);
				textView.setText(radioButton.getText());
			}

		}
	}

	@Override public void onBackPressed()
	{
		new AlertDialog.Builder(this).setTitle("Logout in corso").setMessage("Tornando indietro si ritorna alla schermata di home effettauando così un Logout, sicuro di voler procedere?")
				.setNegativeButton(android.R.string.no, null).setPositiveButton(R.string.si, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface arg0, int arg1)
			{

				Intent intent = new Intent(ResearchSettingsActivity.this, com.example.giuliodimaria.myapplication.MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			}
		}).create().show();
	}
}
