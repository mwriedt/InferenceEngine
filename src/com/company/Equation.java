package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 19/05/2017.
 */

public class Equation
{
    private boolean value;
    private ArrayList<String> arguments = new ArrayList<>();

    public Equation()
    {
        value = false;
    }

    public void setValue(boolean tempBool)
    {
        value = tempBool;
    }

    public void Clear()
    {
        value = false;
        arguments.removeAll(arguments);
    }

    public boolean getValue()
    {
        return value;
    }

    public void addArgument(String tempArgument)
    {
        arguments.add(tempArgument);
    }

    public ArrayList<String> getArguments()
    {
        return arguments;
    }

}
