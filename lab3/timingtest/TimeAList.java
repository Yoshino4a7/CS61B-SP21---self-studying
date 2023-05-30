package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void createAList(int N) {
        // TODO: YOUR CODE HERE
        AList<Integer> a=new AList<Integer>();
        for(int i=0;i<N;i++){
        a.addLast(i);
        }

    }
    public static void roundN(){

    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        int N=100;
        AList<Integer> ns=new AList<Integer>();
        AList<Double> times=new AList<Double>();
        AList<Integer> opCounts=new AList<Integer>();
        for(int i=0;i<7;i++)
        {

            Stopwatch sw = new Stopwatch();
            createAList(N);
            double timeInSeconds = sw.elapsedTime();

            ns.addLast(N);
            times.addLast(timeInSeconds);
            opCounts.addLast(N);
            N*=2;
        }





        printTimingTable(ns, times, opCounts);
    }
}
