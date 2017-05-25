package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 9/05/2017.
 */

public class Model //A model is a model of symbols that contain a True/False value along with its symbol
{
    private List<Symbol> modelList = new ArrayList<>(); //Container for the model, contains symbols which has true/false values

    public Model add(String id, Boolean value)//We call this method if we want to add a symbol to a model along with its True/False value
    {
        modelList.add(new Symbol(id, value)); //Add a new symbol to the model
        return this;//Return this model
    }

    public List<Symbol> GetModel()
    {
        return modelList; //Get the model from this object
    }

    public Model Copy()//A deep copy for a model
    {
        Model temp = new Model();
        for(Symbol s:modelList) //Copy every symbol from the current model to a temp model
        {
            temp.add(s.getId(),s.getValue());
        }
        return temp; //Return the temp model
    }
}
