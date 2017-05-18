package com.company;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 9/05/2017.
 */
public class Model
{
    private List<Symbol> model = new ArrayList<>(); //Container for the model, contains symbols which has true/false values

    public Model()
    {

    }

    public Model add(String id, Boolean value)
    {
      //  if (FindValue(id))
        //{
            model.add(new Symbol(id, value)); //Add a new symbol to the model
        //}
           // System.out.println("ADDING");
        return this;
    }

    public Model Copy()
    {
        Model temp = new Model();
        for(Symbol s:model)
        {
            temp.add(s.getId(),s.getValue());
        }
        return temp;
    }

    private boolean FindValue(String id)
    {
        for(Symbol s: model)
        {
            if (s.getId() == id)
            {
                return false;
            }
        }
        return true;
    }


    public boolean get(int i)
    {
        return model.get(i).getValue();
    }

    public void Empty() //Testing, maybe needed for the CheckALL function
    {
        for(int i = 0; i < model.size(); i++)
        {
            model.remove(i);
        }
    }

}
