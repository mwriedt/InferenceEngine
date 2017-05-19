package com.company;

import com.sun.tracing.Probe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb on 18/05/2017.
 */
public class ProblemSet
{
    public List<String> KnowledgeBase;
    public List<String> Query;

    public ProblemSet(List<String> KB, List<String> Q)
    {
        KnowledgeBase = KB;
        Query = Q;
    }

}
