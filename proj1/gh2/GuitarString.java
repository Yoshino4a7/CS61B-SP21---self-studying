package gh2;

// TODO: uncomment the following import once you're ready to start this portion
 import deque.ArrayDeque;
 import deque.Deque;
 import deque.LinkedListDeque;
 import edu.princeton.cs.algs4.StdRandom;//随机函数
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static double DECAY = .996; // energy decay factor

    public void decayD(){
        DECAY=1.0;
    };

    public void decayR(){
        DECAY=.996;
    };

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.

        int i=0;
        int capacity=(int)Math.round(SR/frequency);
        buffer=new LinkedListDeque<Double>();
        while(i<capacity)
        {
            double r=0.0;
            buffer.addLast(r);
            i++;
        }



    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        int i=0;
        while(i<buffer.size())
        {
            double r=StdRandom.uniform(-0.5,0.5);

            buffer.removeFirst();
            buffer.addLast(r);
            i++;
        }
//        buffer.printDeque();
//        System.out.println("");
//        System.out.print(buffer.get(1)+" ");
//        System.out.print(buffer.get(2)+" ");
//        System.out.print(buffer.get(3)+" ");
//        System.out.print(buffer.get(4)+" ");


    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        double sample=sample();
        buffer.removeFirst();
        buffer.addLast(sample);
//        System.out.print(buffer.get(11));
//        System.out.println("");
//        buffer.printDeque();

    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        double front_1=buffer.get(1);
        double front_2=buffer.get(2);
//        System.out.println("");
//        System.out.println(front_1+"+"+front_2+"="+(front_1+front_2)/2*DECAY);


        return (front_1+front_2)/2*DECAY;


    }

    public static void main(String[] args) {
        GuitarString buffer=new GuitarString(4000);
        buffer.pluck();
        buffer.tic();

        System.out.println("");



    }
}
    // TODO: Remove all comments that say TODO when you're done.
