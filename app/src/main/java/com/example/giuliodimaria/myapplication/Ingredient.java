package com.example.giuliodimaria.myapplication;

class Ingredient
{
    private String nameIng;
    private String quantita;
    private String nameRecipe;

    public Ingredient(String nameIng, String quantita, String nameRecipe)
    {
        this.nameRecipe = nameRecipe;
        this.nameIng = nameIng;
        this.quantita = quantita;
    }

    public Ingredient()
    {}

    public String getNameIng()
    {
        return nameIng;
    }

    public void setNameIng(String nameIng)
    {
        this.nameIng = nameIng;
    }

    public String getQuantita()
    {
        return quantita;
    }

    public void setQuantita(String quantita)
    {
        this.quantita = quantita;
    }

    public String getNameRecipe()
    {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe)
    {
        this.nameRecipe = nameRecipe;
    }

    @Override
    public String toString()
    {
        return nameIng + ": " + quantita;
    }


}
