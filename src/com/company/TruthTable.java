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

    private int count = 0;

    public TruthTable()
    {
        code = "TT";
        longName = "Truth Table";
        knowledgeBase = new ArrayList<String>(); //The KB of the problem
        query = new ArrayList<String>(); //The Query of the problem
    }

    public void SetValues(ProblemSet tempProblemSet)
    {
        knowledgeBase = new ArrayList<>(tempProblemSet.KnowledgeBase);
        query = new ArrayList<>(tempProblemSet.Query);
    }

    public boolean Entails()
    {
        List<String> symbols; //List of all symbols in the knowledge base and query, empty right now
        Model model = new Model(); //The model, empty
        symbols = getSymbols(knowledgeBase, query); //Set the symbols based on the Knowledgebase and query
        List<List<String>> Sentences = GetSentences(knowledgeBase); //Set the sentences based on the knowledgebase
        return CheckAll(Sentences, query,symbols, model); //Return **
    }

    public boolean CheckAll(List<List<String>> Sentences, List<String> query, List<String> symbols,Model model)
    {
        String P;
        if ((symbols.size() <= 0)) //if there are still symbols in the list
        {
            if (PLTrue(Sentences, model)) //If the model satisfies the knowledgebase
            {
                count++;
                System.out.println(count);
                if (PLTrue(model, query))
                {
                    count++;
                    System.out.println(count);
                }
                return PLTrue(model, query); //If the model satisfies the query
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
                return (CheckAll(Sentences, query, Rest, model.add(P,true)) && CheckAll(Sentences, query, Rest, tempModel.add(P,false))); //Recursively return CheckALL with the new model and symbols to create the truth table
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

    private boolean PLTrue(Model model, List<String> query)
    {
        Equation left = new Equation();
        left.Clear();
        for (String x:query)
        {
            if (x != "=>")
            {
                left.addArgument(x);
            }
        }
        left.setValue(EvaluateSide(left, model));

        return left.getValue();
    }

    private boolean PLTrue(List<List<String> > sentences, Model model)
    {
        Equation left = new Equation();
        boolean leftToRight;
        Equation right = new Equation();
        for (List<String> s:sentences)
        {
            right.Clear();
            left.Clear();
            leftToRight = false;
            if (s.contains("=>"))
            {
                for (String x:s)
                {
                    if (!x.equals("=>"))
                    {
                        if (leftToRight)
                        {
                            //Right Side of equation
                            right.addArgument(x);
                        }
                        else
                        {
                            //Left Side of equation
                            left.addArgument(x);
                        }
                    }
                    else
                    {
                        leftToRight = true;
                    }
                }
                //System.out.println(left.getArguments());
                //System.out.println(right.getArguments());
                left.setValue(EvaluateSide(left, model));
                right.setValue(EvaluateSide(right, model));
                if (left.getValue() && !right.getValue())
                {
                    return false;
                }
            }
            else
            {
                for (String x:s)
                {
                    left.addArgument(x);
                }
                left.setValue(EvaluateSide(left, model));
              //  return left.getValue();
            }

        }
        //Only need to find if left is true and right is false, if that return false, else return true
        return true;
    }


    private boolean EvaluateSide(Equation tempSide, Model tempModel)
    {
        if (tempSide.getArguments().size() == 1)
        {
             return Single(tempSide.getArguments().get(0), tempModel);
        }
        else
        {
            for (int i = 0; i < tempSide.getArguments().size(); i++)
            {
                //System.out.println(tempSide.getArguments().size());
                //System.out.println(tempSide.getArguments());
                if (tempSide.getArguments().get(i) == "&")
                {
                    if (!And(tempSide.getArguments().get(i-1), tempSide.getArguments().get(i+1), tempModel))
                    {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    private boolean Single(String left, Model tempModel)
    {
        boolean leftBool = false;
        for(Symbol s: tempModel.GetModel())
        {
            if (left == s.getId())
            {
                leftBool = s.getValue();
            }
        }
        return leftBool;
    }

    private boolean And(String left, String right, Model tempModel)
    {
        boolean leftBool = false;
        boolean rightBool = false;
        for(Symbol s: tempModel.GetModel())
        {
            if (left == s.getId())
            {
                leftBool = s.getValue();
            }
            if (right == s.getId())
            {
                rightBool = s.getValue();
            }
        }
        return (leftBool && rightBool);
    }
    //Gets a list of the symbols from the Knowledgebase and the query, avoids duplicates/
    public List<String> getSymbols(List<String> KB, List<String> Q)
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


    //Returns a list of the sentences in the knowledge base
    private List<List<String>> GetSentences(List<String> knowledgeBase)
    {
        List<List<String>> Sentences = new ArrayList<>();
        List<String> sentence = new ArrayList<>();

        for (String s : knowledgeBase)
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
