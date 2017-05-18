package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb on 18/05/2017.
 */

public abstract class SearchMethod
{
    public String code;
    public String longName;
    public List<String> knowledgeBase;
    public List<String> query;

    public abstract boolean Entails();

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
}
