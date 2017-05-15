package com.company;

import com.sun.jmx.snmp.SnmpUnknownModelLcdException;

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
    private List<String> query = new ArrayList<String>();

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
        List<List<String>> Sentences = GetSentences(knowledgeBase);
        return CheckAll(knowledgeBase, query,symbols, model);
    }

    public boolean CheckAll(List<String> KB, List<String> query, List<String> symbols,Model model)
    {
        String P;

        if ((symbols.size() <= 0)) //if there are still symbols in the list
        {
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
                P = symbols.get(0);         //get the first symbol in the list
                List<String> Rest = new ArrayList<>(symbols);
                Rest.remove(0);    //and remove it from the list

                return (CheckAll(KB,query,Rest, model.add(P,true)) && CheckAll(KB,query,Rest, model.add(P,false)));
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

//        for (List<Symbol> Symbols: model)
//        {
//            for (List<String> sentence: Sentences)
//            {
//                // if sentence is false, return false?
//            }
//        }

        //for each sentence in listValue
        //  if sentence doesn't hold true to query
        //      return false
        //  end if
        //end for

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
                if (!result.contains(s))
                {
                    result.add(s);
                }
            }
        }
        //String[] temp = result.toArray(new String[result.size()]);
        return result;
    }

    private List<List<String>> GetSentences(List<String> listValue)
    {
        List<List<String>> Sentences = new ArrayList<>();
        List<String> sentence = new ArrayList<>();

        for (String s : listValue)
        {
            if (s.equals(";"))
            {

                Sentences.add(sentence);
                sentence = new ArrayList<>();
            }
            else
            {
                sentence.add(s);
            }
        }

        System.out.println("");
        System.out.println("TELL:");
        for(List<String> s: Sentences)
        {
            for (String t: s)
            {
                System.out.print(t);
            }
            System.out.println();
        }
        return Sentences;
    }
}
