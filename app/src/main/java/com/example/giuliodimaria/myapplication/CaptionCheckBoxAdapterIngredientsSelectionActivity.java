package com.example.giuliodimaria.myapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class CaptionCheckBoxAdapterIngredientsSelectionActivity extends RecyclerView.Adapter<CaptionCheckBoxAdapterIngredientsSelectionActivity.ViewHolder>
{
    private String[] captions;//In my case the checkbox ingredients
    private CheckBox checkBox;
    private static ArrayList<String> selectedIngredientsNames;


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //Define the view to be used for each data item
        private CardView cardView;

        public ViewHolder(CardView v)
        {
            super(v);
            this.setIsRecyclable(false);
            cardView = v;
        }

    }

    public CaptionCheckBoxAdapterIngredientsSelectionActivity(String[] captions,ArrayList<String> selectedIngredientsNames)
    {
        this.captions = captions;
        this.selectedIngredientsNames = selectedIngredientsNames;
    }

    @Override
    public int getItemCount()
    {
        return captions.length;
    }


    @Override
    public CaptionCheckBoxAdapterIngredientsSelectionActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned, parent, false);
        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final CardView cardView = holder.cardView;
        checkBox = cardView.findViewById(R.id.checkbox);
        if(selectedIngredientsNames.contains(captions[position]))
            checkBox.setChecked(true);
        checkBox.setText(captions[position]);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    IngredientsSelectionActivity.anElementHasBeenClicked(captions[position]);
                }
                else
                {
                    IngredientsSelectionActivity.anElementHasBeenUnclicked(captions[position]);
                }
            }
        });
    }
}
