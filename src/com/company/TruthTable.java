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
                if (PLTrue(model, query))
                {
                    count++; //TEMP
                   // System.out.println(count);//TEMP
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

    public int getCount()
    {
        return count;
    }

    private boolean PLTrue(Model model, List<String> query) //Evaluate the model against the query
    {
        Equation left = new Equation(); //If there is only a query, we only need one side
        left.Clear();
        for (String x:query)//For all strings in the query
        {
            //if (x != "=>")
            //{
                left.addArgument(x); //Add the arguments to the side
            //}
        }
        left.setValue(EvaluateSide(left, model)); //Set the value based on the model

        return left.getValue(); //Return this value
    }

    private boolean PLTrue(List<List<String> > sentences, Model model)//Evaluate the model against the knowledge base
    {
        Equation left = new Equation(); //Left side of the "=>"
        boolean leftToRight;
        Equation right = new Equation(); //Right side of the "=>"
        for (List<String> s:sentences) //For each sentence
        {
            right.Clear(); //Clear all previous sentence values
            left.Clear(); //Clear all previous sentence values
            leftToRight = false; //Left has not been completed yet
            if (s.contains("=>")) //If the sentence contains a "=>" then it is more than 1 string
            {
                for (String x:s) //For each string in a sentence
                {
                    if (!x.equals("=>")) //If the string is not "=>" then it is a symbol
                    {
                        if (leftToRight) //Has the left side been completed?
                        {
                            //Right Side of equation
                            right.addArgument(x); //Add symbols to the right side
                        }
                        else
                        {
                            //Left Side of equation
                            left.addArgument(x); //Add symbols to the left side of teh equation
                        }
                    }
                    else
                    {
                        leftToRight = true; //Left side has been completed, move to right side now
                    }
                }
                //System.out.println("left: " + left.getArguments());
                //System.out.println("right: " + right.getArguments());
                left.setValue(EvaluateSide(left, model));//Does the model hold true in this sentence?
                right.setValue(EvaluateSide(right, model)); //Does the model hold true in this sentence?
                if (left.getValue() && !right.getValue()) //If the left side is true and the right side is false then the model does not satisfy the knowledge base
                {
                    return false;
                }
            }
            else //If the sentence in only one string
            {
                for (String x:s)
                {
                    left.addArgument(x); //Add this string
                }
               // System.out.println("Single: " + left.getArguments());
                left.setValue(EvaluateSide(left, model)); //Evaluate the model to the sentence
                if (!left.getValue())
                {
                    return false; //If it does not satisfy the kb, return false
                }
            }

        }
        //Only need to find if left is true and right is false, if that return false, else return true
        return true;
    }


    private boolean EvaluateSide(Equation tempSide, Model tempModel) //Evaluate the side against the model
    {
        if (tempSide.getArguments().size() == 1) //If the side is one string
        {
             return Single(tempSide.getArguments().get(0), tempModel); //Return based on value
        }
        else
        {
            for (int i = 0; i < tempSide.getArguments().size(); i++) //For each string in the side
            {
                if (tempSide.getArguments().get(i).equals("&")) //If the symbol is a "&"
                {
                    if (!And(tempSide.getArguments().get(i-1), tempSide.getArguments().get(i+1), tempModel)) //Then the left and right of that symbol need to be evaluated, return if false
                    {
                        return false;
                    }
                }
                else if(tempSide.getArguments().get(i).equals("|")) //If the symbol is a "|"
                {
                    if (!Or(tempSide.getArguments().get(i-1), tempSide.getArguments().get(i+1), tempModel))
                    {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    private boolean Single(String left, Model tempModel)//If there is only one string in the sentence
    {
        boolean leftBool = false;
        for(Symbol s: tempModel.GetModel()) //For each symbol in the model
        {
            if (left.equals(s.getId())) //If the string in the side is the one in the model, get that symbols value
            {
                leftBool = s.getValue();
            }
        }
        return leftBool;
    }

    private boolean Or(String left,String right, Model tempModel) //If you are "Or"ing values
    {
        boolean leftBool = false; //Init to false
        boolean rightBool = false; //Init to false
        for(Symbol s: tempModel.GetModel()) //For every symbol in the model
        {
            if (left.equals(s.getId())) //If we find the symbol id of the side
            {
                leftBool = s.getValue(); //Get that symbols value
            }
            if (right.equals(s.getId()))
            {
                rightBool = s.getValue();
            }
        }
        return (leftBool || rightBool); //Return if either are true
    }

    private boolean And(String left, String right, Model tempModel) //If you are adding values together
    {
        boolean leftBool = false; //Init to false
        boolean rightBool = false; //Init to false
        for(Symbol s: tempModel.GetModel()) //For every symbol in the model
        {
            if (left.equals(s.getId())) //If we find the symbol id of the side
            {
                leftBool = s.getValue(); //Get that symbols value
            }
            if (right.equals(s.getId()))
            {
                rightBool = s.getValue();
            }
        }
        return (leftBool && rightBool); //Return if they are both true
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

//        System.out.println("");
//        System.out.println("TELL:");
//        for(List<String> s: Sentences)
//        {
//            for (String t: s)
//            {
//                System.out.print(t);
//            }
//            System.out.println();
//        }
        return Sentences;
    }
}
