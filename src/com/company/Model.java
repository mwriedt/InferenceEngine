package com.company;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell on 9/05/2017.
 */
public class Model {
    private List<Symbol> model = new ArrayList<>();

    public Model()
    {

    }

    public Model add(String id, Boolean value)
    {
        model.add(new Symbol(id, value));
        return this;
    }


}
