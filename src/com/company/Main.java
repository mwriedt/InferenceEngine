package com.company;
import javafx.scene.shape.TriangleMesh;

import java.io.*;
import java.util.Arrays;

public class Main
{
    //the number of methods programmed into InferenceEngine
    public static final int METHOD_COUNT = 3;
    public static ProblemSet problemSet;
    public static SearchMethod[] lMethods;

    private static void InitMethods()
    {
        lMethods = new SearchMethod[METHOD_COUNT];
        lMethods[0] = new TruthTable();
        lMethods[1] = new BackwardChaining();
        lMethods[2] = new ForwardChaining();

    }


    public static void main(String[] args) {
	    InitMethods();
        Parser parseFile = new Parser(); //Create the parsing object
	    //readProblemFile(args[0]); //Just for debugging purposes

        //args contains
        //  [1] - filename containing problem
        //  [2] - method name


        if(args.length < 2)
        {
            System.out.println("Usage: Main <filename> <search-method>.");
            System.exit(1);
        }

        //get the method from the file
        ProblemSet tempProblemSet = parseFile.readProblemFile(args[1]); //Read the file TEMP

        String method = args[0];
        SearchMethod thisMethod = null;

        //determine which method to use
        for(int i = 0; i < METHOD_COUNT; i++)
        {
            if(lMethods[i].code.compareTo(method) == 0)
            {
                //use this method
                thisMethod = lMethods[i];
            }
        }

        //check if method has been implemented
        if(thisMethod == null)
        {
            //No, return an error
            System.out.println("Method identified by " + method + " does not exist. Method codes are Case Sensitive!");
            System.exit(1);
        }
        else
        {
            thisMethod.SetValues(tempProblemSet);
        }


        boolean solution = thisMethod.Entails();
        switch (thisMethod.code)
        {
            case "TT":
                if (solution)
                {
                    System.out.println("YES: " + thisMethod.getCount());
                }
                else
                {
                    System.out.println("NO");
                }

            case "FC":
            case "BC":
        }
       // System.out.println(solution);

        System.exit(0);
    }
}
