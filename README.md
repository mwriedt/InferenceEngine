Student Details:
ESP Group: COS30019_A02_T025

Mitchell Wriedt: 101114698

Marea Santiano: 101113048

Caleb Darby: 101109487



Features/Bugs/Missing:

Our program has all required basic functions:

Features:

-	Parsing in a text file
-	Basic FC functionality (Can successfully forward chain, but only with the “=>” and “&” symbols)
-	Basic BC functionality(Can successfully backward chain, but only with the “=>” and “&” symbols)
-	Basic TT functionality(Can successfully create a truth table, but only with the “=>”, “&” and “|” symbols)

Missing: 

-	Any functionality that requires parenthesis “()” or bidirectional “” symbols.  

Test Cases:

We used multiple test cases to test our code; we have found no bugs with our code.

“TELL

p2=> p3; p3 => p1; c => e; b&e => f; f&g => h; p1=>d; p1&p3 => c; a; b; p2;

ASK

d”

“TELL

a&b&c => e; e=>f; f&g => i; f&v=>j; j=>g; v; a; b; c;

ASK

g” 


Our solution:

Our solution does operate in a simple command-line form to support batch testing and returns a YES or NO depending on whether the query follows from the knowledgebase.

Truth table follows a very similar structure to the pseudocode seen in Artificial Intelligence: A Modern Approach such that it contains an Entails function, CheckAll function and PLTrue function. This class reads in the parsed file, stores each string as symbols, creates sentences based on these symbols and stores the query in order to call the function CheckAll. Here the symbols are used to make many different models and calls PLTrue which will return true if the query holds within a model. If the query does hold within a model “YES: ” and the number of times the model satisfies both the query and the knowledgebase. If not, “NO” will be returned.

Forward Chaining also follows a very similar structure to the pseudocode seen in Artificial Intelligence: A Modern Approach such that a list of integers Count is used to keep track of how many premises of each implication are unknown and a list of string Agenda which is a queue of symbols that initially known to be true in the KB. Another list of strings areTrue keeps track the symbols that are true which is printed after YES or NO. Forward Chaining removes the first string from Agenda and uses this to search through the clauses to see if the query can be entailed by the knowledge base in a while loop. If the symbol can be found in the clauses, the count is decremented. If it can be entailed, it will return true and “YES: “ followed by the symbols known to be true along with the query. If not, “NO” will be printed.

Backward Chaining uses a function bcEntails to take a Symbol from Agenda (agenda originally contains the query) and first add it to the entailed array, then check if it is in the facts array. If it is, we return true. If it isn’t, we then iterate through the clauses to find a sentence with the symbol on the right side of the entailment, and if we do, we put the symbols on the left of that sentence into Agenda and start the loop again. When true is returned, it prints “YES: “, and the path to the goal, to the console. If bcEntails returns false, it prints “NO”.



Acknowledgements/Resources:

- http://snipplr.com/view/56297/ai-backward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases/ - helped to understand how Backward Chaining worked.

- http://snipplr.com/view/56296/ai-forward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases/ - this website contains a simple implementation of Forward Chaining which helped to further understand the pseudocode in the textbook.

- Artificial Intelligence: A Modern Approach by Peter Norvig and Stuart J. Russell.  – Pseudo code for the truth table and forward chaining was from this textbook. Truth Table and Forward Chaining contain very similar functions found in this textbook.

- COS30019 Introduction to Artificial Intelligence – Assignment 1 Part A (nPuzzler.java readProblemFile function written by COS30019): The error handling found in the Parsing file is based on the error handling in the first assignment. This has allowed us to successfully parse in a text file with error handling and being able to print a solution for the possible error.

- Devon, 2015, ‘InferenceEngine’, GitHub, viewed 19/5/2017, https://github.com/froggerman330/InferenceEngine – we have used test3.txt from this GitHub to further test our program with a different knowledge base and query. This has allowed us to gain a further understanding of the accuracy of our programs. We have also used their spreadsheet with the solutions for each test.txt (testing.xlsx) to confirm our results.



Summary Report:

During our program, we each did a fair amount of the work. 

Marea: Parsing, Forward Chaining: 33%

Caleb: Backward Chaining, Search Method, Problem Set: 33%

Mitchell: Truth Table, Equation, Model, Symbol: 34%

Mitchell undertook creating the Truth Table, which was quite difficult at first to make, along the way creating the Truth Table he has to create the Equation, Model and Symbol objects to help manage the data of the Truth Table. The equation was used to evaluate a statement, the model would hold each symbol from the knowledge base and a Symbol would consist of an Id and a value for that symbol. Mitchell provided feedback on parsing and Forward Chaining.

Caleb was responsible for creating the Backwards Chaining. The main problem encountered when creating this was the lack of resources available (no algorithm in the textbook and very few examples found to show how it works). He also created the “SearchMethod” superclass to the three algorithms as well as the “ProblemSet” object which held the knowledge base and query for the given problem. Caleb provided feedback on the Truth Table.

Marea was responsible for parsing in a text file and creating the Forward Chaining. The knowledge base and query is stored in a list of strings and split appropriately for use throughout the program. Solely based on the pseudocode found in the textbook, she found the concept of keeping track of the count per clause confusing until she found the website stated above. Marea provided feedback on the Truth Table results.
