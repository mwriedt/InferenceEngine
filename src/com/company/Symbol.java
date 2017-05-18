package com.company;

/**
 * Created by Mitchell on 9/05/2017.
 */
public class Symbol {

    private String id;
    private Boolean value;
    public Symbol(String tempId, Boolean tempValue) //A symbol needs an id and a value(True/False)
    {
        id = tempId;
        value = tempValue;
    }

    public String getId()
    {
        return id;
    }

    public Boolean getValue()
    {
        return value;
    }
}
