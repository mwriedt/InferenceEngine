package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 19/05/2017.
 */

public class Equation
{
    private boolean value; //True/False of the equation
    private ArrayList<String> arguments = new ArrayList<>(); //Arguments inside the equation

    public Equation()
    {
        value = false; //init to false
    }

    public void setValue(boolean tempBool)
    {
        value = tempBool; //Set the value of the equation
    }

    public void Clear()
    {
        value = false; //Init back to false
        arguments.removeAll(arguments); //Init to empty
    }

    public boolean getValue()
    {
        return value; //Get the value of the equation
    }

    public void addArgument(String tempArgument)
    {
        arguments.add(tempArgument); //Add an argument to the equation
    }

    public ArrayList<String> getArguments()
    {
        return arguments;//Return the arguments
    }

}
