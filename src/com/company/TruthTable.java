package com.company;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Mitchell on 6/05/2017.
 */
public class TruthTable
{
    private List<String> knowledgeBase = new ArrayList<String>();
    private String query;

    public void SetSymbols(List<String> tempSymbols)
    {
        //knowledgeBase = tempSymbols;
        knowledgeBase = new ArrayList<String>(tempSymbols);
    }

    public void SetQuery(String tempQ)
    {
        query = tempQ;
        //System.arraycopy(tempQ, 0, query, 0, tempQ.length()); //Get the symbols from the parser
    }

    public boolean Entails()
    {
        List<String> symbols;
        symbols = getSymbols(knowledgeBase, query);
        return CheckAll(knowledgeBase, query,symbols);
    }

    public boolean CheckAll(List<String> KB, String Q, List<String> S)//,String[] model)
    {
        //if symbols is empty then
        //if PL-TRUE(KB,model) then return PL-TRUE(query, model)
        //else return true when KB is false, always return true
        //elso do
        // P = First(Symbols)
        // rest = Rest(Symbols)
        // return (CheckAll(Kb, q, model or {P = true}) and
        // CheckAll(Kb, q, model or {P = false} )
        return true;
    }

    private List<String> getSymbols(List<String> KB, String Q)
    {
        List<String> result = new ArrayList<String>();

        for (String s: KB)
        {
            if (s.equals("=>")|| s.equals("&"))
            {
                result.add(s);
            }
        }
//        for (String s: Q)
//        {
//            if (s == "=>" || s == "&")
//            {
//                result.add(s);
//            }
//        }
        //String[] temp = result.toArray(new String[result.size()]);
        return result;
    }
}
