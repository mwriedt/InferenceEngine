package com.company;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class BackwardChaining extends SearchMethod
{

    public BackwardChaining()
    {
        code = "BC";
        longName = "Backward Chaining";
        knowledgeBase = new ArrayList<String>();
        query = new ArrayList<String>();
    }

    public void SetValues(ProblemSet tempProblemSet)
    {

    }

    public boolean Entails()
    {
        return true;
    }

}
