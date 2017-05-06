package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Mitchell on 6/05/2017.
 */

public class Parser {

    public static TruthTable readProblemFile(String fileName)
    {
        try
        {
            //create file reading objects
            FileReader reader = new FileReader(fileName);
            BufferedReader problem = new BufferedReader(reader);
            TruthTable result;

            //split by TELL
            problem.readLine(); //reads "TELL"
            String readTell = problem.readLine(); //reads KB
            ParseStateString(readTell); // FIX: assign to object

            //split by ASK
            problem.readLine(); //reads "ASK"
            String readAsk = problem.readLine(); //reads query and stores

            System.out.println("Tell: " + readTell);
            System.out.println("Ask: " + readAsk);

            //create the TT object...
            result = new TruthTable();

            problem.close();
            return result;
        }
        catch(FileNotFoundException ex)
        {
            //The file didn't exist, show an error
            System.out.println("Error: File \"" + fileName + "\" not found.");
            System.out.println("Please check the path to the file.");
            System.exit(1);
        }
        catch(IOException ex)
        {
            //There was an IO error, show and error message
            System.out.println("Error in reading \"" + fileName + "\". Try closing it and programs that may be accessing it.");
            System.out.println("If you're accessing this file over a network, try making a local copy.");
            System.exit(1);
        }
        //this code should be unreachable. This statement is simply to satisfy Eclipse.
        return null;
    }

    private static void ParseStateString(String stateString)
    {
        //remove spaces
        String noSpaces = stateString.replaceAll("\\s", "");

        //split the string by => or ; or &
        String[] symbols = noSpaces.split("(?<==>)|(?==>)|(?<=;)|(?=;)|(?<=&)|(?=&)");

        System.out.println("parse: " + Arrays.toString(symbols));

        //put into table

        //return something. change void
    }
}
