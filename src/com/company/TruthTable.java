package com.company;

import java.lang.*;


/**
 * Created by Mitchell on 6/05/2017.
 */
public class TruthTable
{
    private String[] symbols;

    public void SetSymbols(String[] tempSymbols)
    {
        System.arraycopy(tempSymbols, 0, symbols, 0, tempSymbols.length); //Get the symbols from the parser
    }
}
