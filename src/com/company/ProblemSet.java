package com.company;

import java.util.List;

/**
 * Created by Caleb on 18/05/2017.
 */
public class ProblemSet
{
    public List<String> KnowledgeBase;
    public List<String> Query;

    public ProblemSet(List<String> KB, List<String> Q) //A problem set is the main data given to the program from the text file
    {
        KnowledgeBase = KB; //This is the Knowledge base given to the program
        Query = Q; //This is the query asked by the text file
    }

}
