package com.company;

import java.lang.*;
import java.util.ArrayList;

public class BackwardChaining extends SearchMethod
{
    public static ArrayList<String> agenda;     //The queue for what needs to be expanded and explored next. starts as the goal state
    public static ArrayList<String> facts;      //sentences we know to be true (e.g. "A")
    public static ArrayList<String> clauses;    //sentences with entailment (e.g. "A=>B")
    public static ArrayList<String> entailed;   //list that represents the path from a fact to the goal
    public static String path;                  //the path from a fact to the queue

    public BackwardChaining()
    {
        code = "BC";
        longName = "Backward Chaining";
        knowledgeBase = new ArrayList<String>();
        query = new ArrayList<String>();
        agenda = new ArrayList<String>();
        facts = new ArrayList<String>();
        clauses = new ArrayList<String>();
        entailed = new ArrayList<String>();
    }

    public void SetValues(ProblemSet tempProblemSet)
    {
        knowledgeBase = tempProblemSet.KnowledgeBase;
        query = tempProblemSet.Query;

        String str = knowledgeBase.toString();          //
        str = str.substring(1, str.length()-1);         //create a substring without the [] around the kb
        str = str.replace(",", "");                     //remove the ","s from the kb
        str = str.replace(" ", "");                     //remove the " "s from the kb

        agenda.add(query.toString().substring(1,2));

        String[] sentences = str.split(";");        //split the kb into sentences (separated by semicolons)
        for (int i = 0; i < sentences.length; i++)
        {
            if (!sentences[i].contains("=>"))           //if the sentence doesn't contains an entailment...
                facts.add(sentences[i]);                //add it to facts (We will always assume there are true)
            else
                clauses.add(sentences[i]);              //otherwise, put them in the clauses list
        }
    }

    public boolean Entails()
    {
        Boolean Result = false;

        if (bcEntails())                //if a path is found to the goal
        {
            Result = true;
            path = "Yes: ";             //print the path
            for (int i = entailed.size()-1; i >= 0; i--)
            {
                if (i == 0)
                    path += entailed.get(i);
                else
                    path += entailed.get(i) + ", ";
            }
        }
        else
            path = "NO";                //otherwise, print NO

        System.out.println(path);

        return Result;
    }

    public boolean bcEntails()
    {
        while(!agenda.isEmpty())
        {
            //get current symbol to be searched
            String q = agenda.get(agenda.size()-1);

            agenda.remove(agenda.size()-1);

            //add current symbol to the entailed array (This will become a part of the path
            entailed.add(q);

            //if this element is in "facts" array, then we don't need to go further
            if (!facts.contains(q))
            {
                ArrayList<String> p = new ArrayList<String>();
                for (int i = 0; i <clauses.size(); i++)
                {
                    if (conclusionContains(clauses.get(i),q))
                    {
                        ArrayList<String> temp = getPremises(clauses.get(i));
                        for(int j=0;j<temp.size();j++)
                            p.add(temp.get(j));         // add the symbols to a temp array
                    }
                }

                //if there are no ways to reach the current agenda, no path can be found, so return false
                if (p.size() == 0)
                    return false;   //no path can be found, so return false
                else
                {
                    for (int i = 0; i < p.size(); i++)
                    {
                        if (!entailed.contains(p.get(i)))   //otherwise, put all elements in p into agenda
                            agenda.add(p.get(i));           //as long as it isnt already in entailed
                    }
                }
            }

        }
        return true;
    }

    public static ArrayList<String> getPremises(String clause)
    {
        String premise = clause.split("=>")[0];             //get the values from the left side of the clause
        ArrayList<String> temp = new ArrayList<String>();
        String[] conjuncts = premise.split("&");            //if the premise has an "&" then split the premise
                                                            //and test them separately
        for(int i = 0; i < conjuncts.length;i++)
            if(!agenda.contains(conjuncts[i]))              //if the symbols are already in agenda, ignore them
                temp.add(conjuncts[i]);                     //else, add them to the temp array

        return temp;
    }

    public static boolean conclusionContains(String clause, String c)
    {
        //Get the symbol from the right-hand side of the clause
        String conclusion = clause.split("=>")[1];
        //Is the right side of the clause the same as the value being searched?
        if (conclusion.contains(c))
            return true;
        else
            return false;
    }
}
