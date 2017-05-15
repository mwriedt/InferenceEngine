package com.company;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 9/05/2017.
 */
public class Model {
    private List<Symbol> model = new ArrayList<>(); //Container for the model, contains symbols which has true/false values

    public Model()
    {

    }

    public Model add(String id, Boolean value)
    {
        model.add(new Symbol(id, value)); //Add a new symbol to the model
        return this;
    }

    public boolean get(int i)
    {
        return model.get(i).GetValue();
    }

    public void Empty() //Testing, maybe needed for the CheckALL function
    {
        for(int i = 0; i < model.size(); i++)
        {
            model.remove(i);
        }
    }

}
