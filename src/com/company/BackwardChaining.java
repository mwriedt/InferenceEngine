package com.company;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Console;
import java.lang.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackwardChaining extends SearchMethod
{
    public static ArrayList<String> agenda;     //the goal state
    public static ArrayList<String> facts;      //sentences we know to be true (e.g. "A")
    public static ArrayList<String> clauses;    //sentences with entailment (A=>B)
    public static ArrayList<String> entailed;   //
    public static String path;

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

    public String getPath()
    {
        return path;
    }

    public void SetValues(ProblemSet tempProblemSet)
    {
        knowledgeBase = tempProblemSet.KnowledgeBase;
        query = tempProblemSet.Query;

        String str = knowledgeBase.toString();
        str = str.substring(1, str.length()-1);
        str = str.replace(",", "");
        str = str.replace(" ", "");
        agenda.add(query.toString().substring(1,2));

        String[] sentences = str.split(";");
        for (int i = 0; i < sentences.length; i++)
        {
            if (!sentences[i].contains("=>"))
                facts.add(sentences[i]);
            else
                clauses.add(sentences[i]);
        }
    }

    public boolean Entails()
    {
        Boolean Result = false;

        if (bcEntails())
        {
            Result = true;
            path = "Yes: ";
            for (int i = entailed.size()-1; i >= 0; i--)
            {
                if (i == 0)
                    path += entailed.get(i);
                else
                    path += entailed.get(i) + ", ";
            }
        }
        else
            path = "NO";

        System.out.println(path);

        return Result;
    }

    public boolean bcEntails()
    {
        while(!agenda.isEmpty())
        {
            //get current symbol
            String q = agenda.get(agenda.size()-1);

            agenda.remove(agenda.size()-1);

            //add to the entailed array
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
                        for(int j=0;j<temp.size();j++){
                            // add the symbols to a temp array
                            p.add(temp.get(j));
                        }
                    }
                }

                if (p.size() == 0)
                    return false;
                else
                {
                    for (int i = 0; i < p.size(); i++)
                    {
                        if (!entailed.contains(p.get(i)))
                            agenda.add(p.get(i));
                    }
                }
            }

        }
        return true;
    }

    public static ArrayList<String> getPremises(String clause)
    {
        String premise = clause.split("=>")[0];
        ArrayList<String> temp = new ArrayList<String>();
        String[] conjuncts = premise.split("&");

        for(int i = 0; i < conjuncts.length;i++)
            if(!agenda.contains(conjuncts[i]))
                temp.add(conjuncts[i]);

        return temp;
    }

    public static boolean conclusionContains(String clause, String c)
    {
        String conclusion = clause.split("=>")[1];
        if (conclusion.contains(c))
            return true;
        else
            return false;
    }
}
