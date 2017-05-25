package com.company;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mitchell on 6/05/2017.
 */

public class TruthTable extends SearchMethod
{
    private int count = 0; //A variable used to count how many models satisfy the knowledge base and query

    public TruthTable() //Initialise these values to
    {
        code = "TT";
        longName = "Truth Table";
        knowledgeBase = new ArrayList<>(); //The KB of the problem
        query = new ArrayList<>(); //The Query of the problem
    }

    public void SetValues(ProblemSet tempProblemSet) //Set the values of the knowledge base and the query within the Truth Table object
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

    public boolean CheckAll(List<List<String>> sentences, List<String> query, List<String> symbols,Model startModel)
    {
        String P;// Used to get the first Symbol from the list
        if ((symbols.size() <= 0)) //if there are still symbols in the list
        {
            if (PLTrue(sentences, startModel)) //If the model satisfies the knowledge base
            {
                if (PLTrue(startModel, query)) //If the model satisfies the query
                {
                    count++; //Add one to the count
                }
                return PLTrue(startModel, query); //If the model satisfies the query
            }
            else //If the model does not satisy, return true always
            {
                return true;
            }
        }
        else
        {
            P = symbols.get(0);         //get the first symbol in the list
            List<String> Rest = new ArrayList<>(symbols); //The rest of the symbols
            Rest.remove(0);    //and remove it from the list, so that it is the same but one symbol has been removed
            Model tempModel = startModel.Copy(); //Deep copy of the startModel to tempModel
            return (CheckAll(sentences, query, Rest, startModel.add(P,true)) && CheckAll(sentences, query, Rest, tempModel.add(P,false))); //Recursively return CheckALL with the new model and symbols to create the truth table
        }
    }

    public int getCount()
    {
        return count;
    } //Return the count variable

    private boolean PLTrue(Model model, List<String> query) //Evaluate the model against the query
    {
        Equation left = new Equation(); //If there is only a query, we only need one side
        left.Clear(); //Clear out anything within left
        for (String x:query)//For all strings in the query
        {
            left.addArgument(x); //Add the arguments to the side
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
                        return false; //If it fails, return false
                    }
                }
                else if(tempSide.getArguments().get(i).equals("|")) //If the symbol is a "|"
                {
                    if (!Or(tempSide.getArguments().get(i-1), tempSide.getArguments().get(i+1), tempModel))//Get the symbols on either side of this symbol
                    {
                        return false;//If it fails, return false
                    }
                }
            }
            return true;//If nothing fails, return true
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
        return leftBool; //Return the value of this symbol
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

    //Gets a list of the symbols from the Knowledge base and the query, avoids duplicates/
    public List<String> getSymbols(List<String> KB, List<String> Q)
    {
        List<String> result = new ArrayList<>(); //Result variable to store a list of the symbols

        for (String s: KB) //For every string in the knowledge base
        {
            if (!s.equals("=>")&& !s.equals("&") && !s.equals(";"))//If this string is not one of these symbols
            {
                if (!result.contains(s)) //If the result does not already contain the symbol
                {
                    result.add(s); //Add the symbol
                }
            }
        }
        for (String s: Q) //For every string in the query
        {
            if (!s.equals("=>")&& !s.equals("&") && !s.equals(";"))
            {
                if (!result.contains(s))
                {
                    result.add(s);
                }
            }
        }
        return result;
    }

    //Returns a list of the sentences in the knowledge base
    private List<List<String>> GetSentences(List<String> knowledgeBase)
    {
        List<List<String>> Sentences = new ArrayList<>();
        List<String> sentence = new ArrayList<>();

        for (String s : knowledgeBase) //For every String in the knowledge base
        {
            if (s.equals(";")) //If the string equal ";" it mean the end of a sentence has been reached
            {
                Sentences.add(sentence); //Add this sentence to the Sentences list
                sentence = new ArrayList<>(); //Reset the sentence variable
            }
            else
            {
                sentence.add(s); //Add a string to the sentence
            }
        }
        return Sentences; //Return a list of all of the sentences (List of Lists of Strings)
    }
}
