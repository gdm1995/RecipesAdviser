package com.example.giuliodimaria.myapplication;

public class FeedBacks
{
    private String nameIdRecipe;
    private String usernameId;
    private String seen; //0 false 1 true

    public FeedBacks (String nameIdRecipe,String usernameId,String seen)
    {
        this.nameIdRecipe = nameIdRecipe;
        this.usernameId = usernameId;
        this.seen=seen;
    }

    public FeedBacks(String usernameId)
    {
        this.usernameId=usernameId;
    }

    public String getNameIdRecipe()
    {
        return nameIdRecipe;
    }

    public String getUsernameId()
    {
        return usernameId;
    }

    public void setNameIdRecipe(String nameIdRecipe)
    {
        this.nameIdRecipe = nameIdRecipe;
    }

    public void setUsernameId(String usernameId)
    {
        this.usernameId = usernameId;
    }

    public void setSeen(String seen)
    {
        this.seen = seen;
    }

    public String getSeen()
    {
        return seen;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
