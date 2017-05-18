<<<<<<< HEAD
package com.company;
import java.util.*;

/**
 * Created by marea on 16/05/2017.
 */
public class ForwardChaining
{
    private int Count;
    private List<TruthTable> inferred = new ArrayList<TruthTable>();
    private List<TruthTable> agenda = new ArrayList<TruthTable>();

    public ForwardChaining()
    {
        Count = 0;
    }

    private int NumberOfSymbols(TruthTable TT) {
        int NumEle =  TT.returnSymbols().size();

        Count = NumEle;
        System.out.println("number of symbols: " + Count);

        return Count;
    }

    private int QuerySize(TruthTable TT)
    {
        int qSize = TT.returnQuery().size();
        System.out.println("number of symbols: " + qSize);
        return qSize;
    }

    private List assignAgenda(TruthTable TT)
    {
        agenda = TT.returnSentences();
        System.out.println("agenda: " + agenda);

        return agenda;
    }

    public boolean FCEntails(TruthTable TT)
    {
        NumberOfSymbols(TT);
        QuerySize(TT);
        assignAgenda(TT);

        /*
        while(!agenda.isEmpty())
        {

        }*/

        return true;
    }

    public void dequeue(TruthTable TT)
    {

    }
}
=======
package com.company;
import java.util.*;

/**
 * Created by marea on 16/05/2017.
 */
public class ForwardChaining
{
    private int Count;
    private List<TruthTable> inferred = new ArrayList<TruthTable>();
    private List<TruthTable> agenda = new ArrayList<TruthTable>();

    public ForwardChaining()
    {
        Count = 0;
    }

    private int NumberOfSymbols(TruthTable TT) {
        int NumEle =  TT.returnSymbols().size();

        Count = NumEle;
        System.out.println("number of symbols: " + Count);

        return Count;
    }

    private int QuerySize(TruthTable TT)
    {
        int qSize = TT.returnQuery().size();
        System.out.println("number of symbols: " + qSize);
        return qSize;
    }

    private List assignAgenda(TruthTable TT)
    {
        agenda = TT.returnSentences();
        System.out.println("agenda: " + agenda);

        return agenda;
    }

    public boolean FCEntails(TruthTable TT)
    {
        NumberOfSymbols(TT);
        QuerySize(TT);
        assignAgenda(TT);

        /*
        while(!agenda.isEmpty())
        {

        }*/

        return true;
    }

    public void dequeue(TruthTable TT)
    {

    }
}
>>>>>>> master
