package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mitchell on 6/05/2017.
 */

public class Parser
{
    public static ProblemSet readProblemFile(String fileName)
    {
        try
        {
            //create file reading objects
            FileReader reader = new FileReader(fileName);
            BufferedReader problem = new BufferedReader(reader);
            ProblemSet result;

            //split by TELL
            problem.readLine(); //reads "TELL"
            String readTell = problem.readLine(); //reads KB
            List<String> symbols;
            symbols = ParseStateString(readTell);

            //split by ASK
            problem.readLine(); //reads "ASK"

            String readAsk = problem.readLine(); //reads query and stores // Might need to change to list

            List<String> query= new ArrayList<>();
            query.add(readAsk);

            result = new ProblemSet(symbols, query);

            problem.close();
            return result;
        }
        
        // ERROR HANDLING ACKNOWLEDGEMENT: Assignment 1 sourced from nPuzzler.java readProblemFile() written by COS30019
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

    private static List<String> ParseStateString(String stateString)
    {
        //remove spaces
        String noSpaces = stateString.replaceAll("\\s", "");

        //split the string by => or ; or &
        String[] symbols = noSpaces.split("(?<==>)|(?==>)|(?<=;)|(?=;)|(?<=&)|(?=&)");
        List<String> symbolList = Arrays.asList(symbols);

        return symbolList;
        //put into table
    }


}
