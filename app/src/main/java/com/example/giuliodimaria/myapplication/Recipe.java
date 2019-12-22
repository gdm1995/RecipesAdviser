package com.example.giuliodimaria.myapplication;

import java.util.ArrayList;

public class Recipe
{
    private String title;
    private String score;
    private String price;
    private String category;
    private String difficulty;
    private String presentation;
    private ArrayList<Ingredient> ingredients;
    private String preparation;
    private String time;

    public Recipe(String title, String score, String time, String category, String difficulty, String presentation, ArrayList<Ingredient> ingredients,
                  String preparation, String price)
    {
        this.title = title;
        this.score = score;
        this.time = time;
        this.category = category;
        this.difficulty = difficulty;
        this.presentation = presentation;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.price = price;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public Recipe()
    {
    }

    public String getScore()
    {
        return score;
    }

    public void setScore(String score)
    {
        this.score = score;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(String difficulty)
    {
        this.difficulty = difficulty;
    }

    public String getPresentation()
    {
        return presentation;
    }

    public void setPresentation(String presentation)
    {
        this.presentation = presentation;
    }

    public ArrayList<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getPreparation()
    {
        return preparation;
    }

    public void setPreparation(String preparation)
    {
        this.preparation = preparation;
    }


    @Override
    public String toString()
    {
        return "Recipe [title=" + title + ", category=" + category + ", difficulty=" + difficulty + ", presentation="
                + presentation + ", ingredients=" + ingredients + ", preparation=" + preparation + "]";
    }
}


