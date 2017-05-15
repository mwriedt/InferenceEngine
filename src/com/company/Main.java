package com.company;
import javafx.scene.shape.TriangleMesh;

import java.io.*;
import java.util.Arrays;

public class Main
{
    //the number of methods programmed into InferenceEngine
    public static final int METHOD_COUNT = 0;

    public static TruthTable truthTable = new TruthTable();

    private static void InitMethods()
    {
        //enter TT, BC, FC

    }


    public static void main(String[] args) {
	    InitMethods();
        Parser parseFile = new Parser(); //Create the parsing object
        truthTable = parseFile.readProblemFile("test.txt"); //Read the file TEMP
        truthTable.Entails(); //Create the truth table, will be changed later when Forward and Backward chaining are implemented
	    //readProblemFile(args[0]); //Just for debugging purposes
    }
}
