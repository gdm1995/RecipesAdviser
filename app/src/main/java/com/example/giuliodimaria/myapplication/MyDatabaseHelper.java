package com.example.giuliodimaria.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;


public class MyDatabaseHelper extends SQLiteOpenHelper
{
    private static Context context;
    private static final String DB_NAME = "RecipesAdviser";
    private static final int DB_VERSION = 2;
    private static ArrayList<Recipe> ricette;

    public MyDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.ricette = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        updateMyDatabase(db, 0, DB_VERSION);
        //I'm passing the database, the old and new version;
        //The old is 0 because there were no databases installed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        updateMyDatabase(db, oldVersion, newVersion);
        //I'm passsing the database, the old and new version;
        //The old is the database installed and the new is the version in DB_Version
    }

    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //TO lightweight all the info I deleted the static info and put them in shared preferences
        if (oldVersion < 1)
        {
            //Database creation

            //Creation of user table
            db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, password TEXT)");

            //Creation of allergies
            db.execSQL("CREATE TABLE ALLERGIES (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdIngredient TEXT)");

            //Creation of Likes table
            db.execSQL("CREATE TABLE LIKES (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdIngredient TEXT)");

            //Creation of Dislikes table
            db.execSQL("CREATE TABLE DISLIKES (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdIngredient TEXT)");

            //Creation of Feedbacks table
            db.execSQL("CREATE TABLE FEEDBACKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdRecipe TEXT, seen TEXT)");

//            Creation of recipe table
            db.execSQL("CREATE TABLE RECIPE (_id INTEGER PRIMARY KEY AUTOINCREMENT, nameIdRecipe TEXT,score TEXT,time TEXT,category TEXT," +
                    "difficulty TEXT,presentation TEXT,preparation TEXT,price TEXT)");

            //Creation of ingredients table
            db.execSQL("CREATE TABLE INGREDIENTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, nameIdIngredient TEXT,quantita TEXT,nameIdRecipe TEXT)");

            //Passing the database and storage in it all the recipes from the java classes.
            creationData(db);
            //Just to try it out :D
//            ArrayList<Ingredient> a = new ArrayList<Ingredient>();
//            a.add(new Ingredient("ciao", "sono", "io"));
//            a.add(new Ingredient("ciao1", "anche", "a te"));
//            insertRecipe(db, new Recipe("a", "b", "c", "d", "e", "f", a, "g", "h"));
        } else
        {
            //Database modification

            db.execSQL("DROP TABLE IF EXISTS " + "RECIPE");
            db.execSQL("DROP TABLE IF EXISTS " + "INGREDIENTS");
            db.execSQL("DROP TABLE IF EXISTS " + "USER");
            db.execSQL("DROP TABLE IF EXISTS " + "ALLERGIES");
            db.execSQL("DROP TABLE IF EXISTS " + "LIKES");
            db.execSQL("DROP TABLE IF EXISTS " + "DISLIKES");
            db.execSQL("DROP TABLE IF EXISTS " + "FEEDBACKS");


            //Creation of user table
            db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, password TEXT)");

            //Creation of allergies
            db.execSQL("CREATE TABLE ALLERGIES (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdIngredient TEXT)");

            //Creation of Likes table
            db.execSQL("CREATE TABLE LIKES (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdIngredient TEXT)");

            //Creation of Dislikes table
            db.execSQL("CREATE TABLE DISLIKES (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdIngredient TEXT)");

            //Creation of Feedbacks table
            db.execSQL("CREATE TABLE FEEDBACKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, idUser TEXT, nameIdRecipe TEXT, seen TEXT)");

            //Creation of recipe table
            db.execSQL("CREATE TABLE RECIPE (_id INTEGER PRIMARY KEY AUTOINCREMENT, nameIdRecipe TEXT,score TEXT,time TEXT,category TEXT," +
                    "difficulty TEXT,presentation TEXT,preparation TEXT,price TEXT)");

            //Creation of ingredients table
            db.execSQL("CREATE TABLE INGREDIENTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, nameIdIngredient TEXT,quantita TEXT,nameIdRecipe TEXT)");

            //populating
            creationData(db);
        }
    }

    private static void insertIngredients(SQLiteDatabase db, ArrayList<Ingredient> ingredients, String nameIdRecipe)
    {
        db.beginTransaction();

        ContentValues ingredientsValues;
        Ingredient ingredient;
        for (int i = 0; i < ingredients.size(); i++)
        {
            ingredientsValues = new ContentValues();
            ingredient = ingredients.get(i);
            ingredientsValues.put("nameIdIngredient", ingredient.getNameIng());
            ingredientsValues.put("quantita", ingredient.getQuantita());
            ingredientsValues.put("nameIdRecipe", nameIdRecipe);
            db.insert("INGREDIENTS", null, ingredientsValues);
            ingredientsValues.clear();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private static void insertRecipe(SQLiteDatabase db, Recipe recipe)
    {
        db.beginTransaction();

        ContentValues recipeValues = new ContentValues();
        recipeValues.put("nameIdRecipe", recipe.getTitle());
        recipeValues.put("score", recipe.getScore());
        recipeValues.put("time", recipe.getTime());
        recipeValues.put("category", recipe.getCategory());
        recipeValues.put("difficulty", recipe.getDifficulty());
        recipeValues.put("presentation", recipe.getPresentation());
        recipeValues.put("preparation", recipe.getPresentation());
        recipeValues.put("price", recipe.getPrice());
        db.insert("RECIPE", null, recipeValues);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private static void creationData(SQLiteDatabase db)
    {
        //We are going to decompose in attributes the data from the files and save every single recipe and ingredient

        //This method was more dinamic but I'm going to usse a simple switch
//        Field[] declaredFields = Test.class.getDeclaredFields();
//        List<Field> staticFields = new ArrayList<Field>();
//        for (Field field : declaredFields) {
//            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
//                staticFields.add(field);
//            }
//        }


//        ContentValues myvalues = new ContentValues();
//        myvalues.put("idUser","Dario");
//        myvalues.put("password","");
//        db.insert("USER", null, myvalues);

        int data = 0;
        int temp1 = 0;
        int offset=0;
        String allData;
        while (data < 4117)//4117 is a magic number for all the recipes
        {
            temp1 = data / 200;
            offset = data % 200;
            switch (temp1)
            {
                case 0: allData=Data0.getDBInfo(offset);break;
                case 1: allData=Data200.getDBInfo(offset);break;
                case 2: allData=Data400.getDBInfo(offset);break;
                case 3: allData=Data600.getDBInfo(offset);break;
                case 4: allData=Data800.getDBInfo(offset);break;
                case 5: allData=Data1000.getDBInfo(offset);break;
                case 6: allData=Data1200.getDBInfo(offset);break;
                case 7: allData=Data1400.getDBInfo(offset);break;
                case 8: allData=Data1600.getDBInfo(offset);break;
                case 9: allData=Data1800.getDBInfo(offset);break;
                case 10: allData=Data2000.getDBInfo(offset);break;
                case 11: allData=Data2200.getDBInfo(offset);break;
                case 12: allData=Data2400.getDBInfo(offset);break;
                case 13: allData=Data2600.getDBInfo(offset);break;
                case 14: allData=Data2800.getDBInfo(offset);break;
                case 15: allData=Data3000.getDBInfo(offset);break;
                case 16: allData=Data3200.getDBInfo(offset);break;
                case 17: allData=Data3400.getDBInfo(offset);break;
                case 18: allData=Data3600.getDBInfo(offset);break;
                case 19: allData=Data3800.getDBInfo(offset);break;
                default: allData=Data4000.getDBInfo(offset);
            }


            //The recipe
            Recipe recipe = new Recipe();

            // The recipe title
            String title = "";

            // The recipe category
            String category = "";

            // The recipe rate
            String score = "";

            // The recipe time
            String time = "";

            // The recipe difficulty
            String diff = "";

            // The recipe presentation
            String presentation = "";

            // The recipe ingredients
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            String nameOfIng = "", quantityIng = "";

            // The recipe Preparation
            String preparation = "";

            // The recipe Cost
            String price = "";

            //Reading every single line
            String temp = "";//A temporary string to save info
            int i = 0; //index to control the string end
            while (allData.length() > i)
            {
                temp += allData.charAt(i);
                if (temp.equals("title="))
                {
                    i++;//we will start from the real title
                    temp = "";//deleting the title part for the next recipe and better catch the title
                    while (!(temp.contains(", score")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    title = temp.substring(0, temp.indexOf(", score"));

                    //catching the score
                    i++;//To skip the =
                    temp = "";//We delete the title string and start to analyse the score
                    while (!(temp.contains(", price")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    score = temp.substring(0, temp.indexOf(", price"));

                    //catching the price
                    i++;//To skip the =
                    temp = "";//We delete the title string and start to analyse the category
                    while (!(temp.contains(", category")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    price = temp.substring(0, temp.indexOf(", category"));

                    //catching the category
                    i++;//To skip the =
                    temp = "";
                    while (!(temp.contains(", difficulty")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    category = temp.substring(0, temp.indexOf(", difficulty"));

                    //catching the difficulty
                    i++;//To skip the =
                    temp = "";
                    while (!(temp.contains(", presentation")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    diff = temp.substring(0, temp.indexOf(", presentation"));

                    //catching the presentation
                    i++;//To skip the =
                    temp = "";
                    while (!(temp.contains(", preparation")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    presentation = temp.substring(0, temp.indexOf(", preparation"));

                    //catching the preparation
                    i++;//To skip the =
                    temp = "";
                    while (!(temp.contains(", time")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    preparation = temp.substring(0, temp.indexOf(", time"));

                    //catching the time
                    i++;//To skip the =
                    temp = "";
                    while (!(temp.contains(", ingredients= [ nameIng")))
                    {
                        temp += allData.charAt(i);
                        i++;
                    }
                    time = temp.substring(0, temp.indexOf(", ingredients= [ nameIng"));

                    //catching ingredients
                    while (!(temp.contains("]" + " end recipe.")))
                    {
                        //catching the ingredient name
                        i++;//To skip the =
                        temp = "";
                        while (!(temp.contains(", quantita =")))
                        {
                            temp += allData.charAt(i);
                            i++;
                        }
                        nameOfIng = temp.substring(0, temp.indexOf(", quantita ="));

                        temp = "";
                        while (!(temp.contains(" ,  nameIng") || (temp.contains(" ]" + " end recipe."))))
                        {
                            temp += allData.charAt(i);
                            i++;
                        }
                        if (temp.contains(" ,  nameIng"))
                            quantityIng = temp.substring(0, temp.indexOf(" ,  nameIng"));
                        else
                            quantityIng = temp.substring(0, temp.indexOf(" ]" + " end recipe."));

                        //Adding the ingredient to the list of ingredients
                        ingredients.add(new Ingredient(nameOfIng, quantityIng, title));
                    }
                    recipe.setTitle(title);
                    recipe.setScore(score);
                    recipe.setPrice(price);
                    recipe.setCategory(category);
                    recipe.setDifficulty(diff);
                    recipe.setPresentation(presentation);
                    recipe.setPreparation(preparation);
                    recipe.setTime(time);
                    recipe.setIngredients(ingredients);

                    ricette.add(recipe);

                    insertRecipe(db, recipe);
                    insertIngredients(db,recipe.getIngredients(),title);
                }
                i++;//We go to the beginning of the new recipe
                //And we scan until there will be title= in temp
            }
            data++;
        }
        //putting the ricette array in sharedPreferences to speed up everything (Json library has been used
        SharedPreferences sharedPref = context.getSharedPreferences(MainActivity.ARRAYRICETTEDB, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ricette);
        editor.putString(MainActivity.ARRAYRICETTEDB,(json));
        editor.apply();
    }

}

//The database imported used to have many foreign keys.
//However we don't want to have that much here 'cause the db tables  will be static for most of the tables.
