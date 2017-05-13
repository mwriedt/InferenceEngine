package com.company;

/**
 * Created by Mitchell on 9/05/2017.
 */
public class Symbol {

    private String id;
    private Boolean value;
    public Symbol(String tempId, Boolean tempValue)
    {
        id = tempId;
        value = tempValue;
    }

    public Boolean GetValue()
    {
        return value;
    }
}
