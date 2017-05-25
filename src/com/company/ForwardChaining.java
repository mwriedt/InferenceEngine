package com.company;
import java.util.*;

/**
 * Created by marea on 16/05/2017.
 */

public class ForwardChaining extends SearchMethod
{
    private List<String> clauses;          // all clauses that are not one symbol
    private List<String> agenda;           // includes single symbols. All initially true in KB but not yet processed
    private List<String> areTrue;          // all true. symbols removed from agenda
    private List<Integer> count;           // stores how many premises of each clause are unknown

    public ForwardChaining()
    {
        code = "FC";
        longName = "Forward Chaining";
        knowledgeBase = new ArrayList<String>();
        query = new ArrayList<String>();
    }

    public void SetValues(ProblemSet tempProblemSet)
    {
        knowledgeBase = tempProblemSet.KnowledgeBase;       // sets knowledge base
        query = tempProblemSet.Query;                       // sets query

        clauses = new ArrayList<>();
        agenda = new ArrayList<>();
        areTrue = new ArrayList<>();
        count = new ArrayList<>();

        String str = knowledgeBase.toString();              // knowledge base to string
        str = str.substring(1, str.length()-1);             // store string in between [ ]
        str = str.replace(",", "");         // remove commas
        str = str.replace(" ", "");         // remove spaces

        String[] sentences = str.split(";");           // split by semi colon

        for(String s: sentences)
        {
            if(!s.contains("=>"))         // if sentence has =>
            {
                agenda.add(s);            // add to agenda if no =>
            }                             // no need to add to clauses since s is initially known to be true in KB
            else
            {
                clauses.add(s);           // add to clauses if there is =>
            }
        }
    }

    private boolean Premise(String clause, String p)
    {
        String temp = clause.split("=>")[0];        // split by => and only store left hand side
        String[] splitTemp = temp.split("&");       // split by & and store both sides

        // if array has length of 1 and temp is p OR if splitTemp contains p
        if (splitTemp.length==1 && temp.equals(p) || Arrays.asList(splitTemp).contains(p))
        {
            return true;
        }
        return false;
    }

    private void initCount()
    {
        for (String s: clauses)                             // foreach string in clauses
        {
            String[] splitClause = s.split("&");            // split by &
            count.add(splitClause.length);                  // return number of symbols on both sides of &
        }
    }

    public boolean Entails()
    {
        initCount();                                        // initialise how many premises of each clause are unknown

        String p;

        while(!agenda.isEmpty())                            // while there are symbols in agenda
        {
            p = agenda.remove(0);                      // remove first item and store in p
            areTrue.add(p);                                 // add p to list of symbols known to be true

            if(p.equals(query.get(0)))                      // if p is the query
            {
                System.out.print("YES: ");                  // print YES:
                printTrue();                                // and print list of symbols known to be true
                return true;                                // query is entailed by knowledge base
            }

            for(int i = 0; i < clauses.size(); i++) {           // for all clauses
                if (Premise(clauses.get(i), p)) {               // if p is in a clause
                    int c = count.get(i);                       // get the count for clause
                    count.set(i, --c);                          // decrement the count for clause
                    Conclusion(count.get(i), clauses.get(i));   // if conclusion can be added to agenda
                }
            }
        }

        System.out.println("NO");           // query is not entailed by knowledge base
        printTrue();                        // print list of symbols that are true anyway
        return false;                       // query is not entailed by knowledge base
    }

    private void printTrue() {
        for(String s: areTrue)              // for each string in areTrue
        {
            System.out.print(s + " ");      // print each one separated by space
        }
    }

    private void Conclusion(int count, String clause)
    {
        if(count == 0) {                                    // if all premises of clause are known
            String rhs = clause.split("=>")[1];             // split clause by => and store the right hand side
            agenda.add(rhs);                                // conclusion can be added to agenda
        }
    }
}


