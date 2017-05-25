package com.company;

/**
 * Created by Mitchell on 9/05/2017.
 */

public class Symbol {

    private String id;
    private Boolean value;

    public Symbol(String tempId, Boolean tempValue) //A symbol needs an id and a value(True/False)
    {
        id = tempId; //ID of the symbol, e.g. "p2" or "a"
        value = tempValue;//Value of the symbol e.g. True/False
    }

    public String getId()
    {
        return id; //Return the ID of the symbol
    }

    public Boolean getValue()
    {
        return value; //Return the value of the symbol
    }
}
