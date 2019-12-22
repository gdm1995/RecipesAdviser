package com.example.giuliodimaria.myapplication;

import java.util.ArrayList;

public class Reliability
{
    private ArrayList<FeedBacks> scores;
    private double reliability;

    public Reliability(ArrayList<FeedBacks> scores, double reliability)
    {
        this.scores = scores;
        this.reliability = reliability;
    }

    public Reliability(){}

    public void setReliability(double reliability)
    {
        this.reliability = reliability;
    }

    public double getReliability()
    {
        return reliability;
    }

    public  ArrayList<FeedBacks> getScores()
    {
        return scores;
    }

    public void addFeedback (FeedBacks score)
    {
        this.scores.add(score);
    }

    public void setScores( ArrayList<FeedBacks> scores)
    {
        this.scores = scores;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
