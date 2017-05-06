package com.company;
import javafx.scene.shape.TriangleMesh;

import java.io.*;
import java.util.Arrays;

public class Main
{
    //the number of methods programmed into InferenceEngine
    public static final int METHOD_COUNT = 0;

    public TruthTable truthTable = new TruthTable();

    private static void InitMethods()
    {
        //enter TT, BC, FC

    }


    public static void main(String[] args) {
	    InitMethods();
        Parser parseFile = new Parser(); //Create the parsing object
        parseFile.readProblemFile("test.txt"); //Read the file TEMP
	    //readProblemFile(args[0]); //Just for debugging purposes
    }
}
