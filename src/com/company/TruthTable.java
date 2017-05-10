package com.company;

import javax.jws.WebParam;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Mitchell on 6/05/2017.
 */

public class TruthTable
{
    private List<String> knowledgeBase = new ArrayList<String>();
    private List<String> query;

    public TruthTable()
    {

    }

    public void SetSymbols(List<String> tempSymbols)
    {
        //knowledgeBase = tempSymbols;
        knowledgeBase = new ArrayList<>(tempSymbols);
    }

    public void SetQuery(String tempQ)
    {
        query.add(tempQ);
        //System.arraycopy(tempQ, 0, query, 0, tempQ.length()); //Get the symbols from the parser
    }

    public boolean Entails()
    {
        List<String> symbols;
        Model model = new Model();
        symbols = getSymbols(knowledgeBase, query);
        return CheckAll(knowledgeBase, query,symbols, model);
    }

    public boolean CheckAll(List<String> KB, List<String> query, List<String> symbols,Model model)
    {
        String P;

        if (symbols.size() <= 0) {
            if (PLTrue(KB, model))
            {
                return PLTrue(query, model);
            }
            else
            {
                return true;
            }
        }
        else
        {
            do
            {
                P = symbols.get(0);
                symbols.remove(0);

                return (CheckAll(KB,query,symbols, model.add(P,true)) && CheckAll(KB,query, symbols, model.add(P,false)));
            }while(true);
        }
        //if symbols is empty then
        //if PL-TRUE(KB,model) then return PL-TRUE(query, model)
        //else return true when KB is false, always return true
        //else do
        // P = First(Symbols)
        // rest = Rest(Symbols)
        // return (CheckAll(Kb, q, model or {P = true}) and CheckAll(Kb, q, model or {P = false} )
        //return true;
    }

    private boolean PLTrue(List<String> listValue, Model model)
    {
        if (listValue.size() >= 1)
        {
            System.out.println("KB");
        }
        else
        {
            System.out.println("Query");
        }
        return true;
    }

    private List<String> getSymbols(List<String> KB, List<String> Q)
    {
        List<String> result = new ArrayList<>();

        for (String s: KB)
        {
            if (!s.equals("=>")&& !s.equals("&") && !s.equals(";"))
            {
                if (!result.contains(s))
                {
                    result.add(s);
                }
            }
        }
        for (String s: Q)
        {
            if (!s.equals("=>")&& !s.equals("&") && !s.equals(";"))
            {
                result.add(s);
            }
        }
        //String[] temp = result.toArray(new String[result.size()]);
        return result;
    }
}
