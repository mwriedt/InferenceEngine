package com.company;

import com.sun.jmx.snmp.SnmpUnknownModelLcdException;

import javax.jws.WebParam;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mitchell on 6/05/2017.
 */

public class TruthTable extends SearchMethod
{

    public TruthTable()
    {
        code = "TT";
        longName = "Truth Table";
        knowledgeBase = new ArrayList<String>(); //The KB of the problem
        query = new ArrayList<String>(); //The Query of the problem
    }

    public boolean Entails()
    {
        List<String> symbols; //List of all symbols in the knowledge base and query, empty right now
        Model model = new Model(); //The model, empty
        symbols = getSymbols(knowledgeBase, query); //Set the symbols based on the Knowledgebase and query
        List<List<String>> Sentences = GetSentences(knowledgeBase); //Set the sentences based on the knowledgebase
        return CheckAll(knowledgeBase, query,symbols, model); //Return **
    }

    public boolean CheckAll(List<String> KB, List<String> query, List<String> symbols,Model model)
    {
        String P;
        if ((symbols.size() <= 0)) //if there are still symbols in the list
        {
            if (PLTrue(KB, model)) //If the model satisfies the knowledgebase
            {
                return PLTrue(query, model); //If the model satisfies the query
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
                List<String> Rest = new ArrayList<>(symbols); //The rest of the symbols
                Rest.remove(0);    //and remove it from the list, so that it is the same but one symbol has been removed
                Model tempModel = model.Copy();
                return (CheckAll(KB, query, Rest, model.add(P,true)) && CheckAll(KB, query, Rest, tempModel.add(P,false))); //Recursively return CheckALL with the new model and symbols to create the truth table
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

        return false;
    }


    //Returns a list of the sentences in the knowledge base
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
