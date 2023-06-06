package deque;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomTest {

    @Test
    public  void testThreeAddThreeRemove(){

        LinkedListDeque<Integer> LDeque = new  LinkedListDeque<>();

        LDeque.addLast(5);
        LDeque.addLast(10);
        LDeque.addLast(15);



        assertEquals(3,LDeque.size());

        int i1=LDeque.removeLast();
        int i2=LDeque.removeLast();
        int i3=LDeque.removeLast();

        assertEquals(15, i1);
        assertEquals(10, i2);
        assertEquals(5, i3);
    }

    @Test
    public void testRandomCall(){
        LinkedListDeque<Integer> LDeque = new  LinkedListDeque<>();

        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            switch(operationNumber)
            {
                case 0:
                {
                    //addFirst
                    LDeque.addFirst(i);
                    System.out.print("addFirst("+i+")");
                    System.out.println("");
                    break;
                }
                case 1:{
                    //addLast
                    LDeque.addLast(i);
                    System.out.print("addLast("+i+")");
                    System.out.println("");
                    break;
                }
                case 2:{
                    //removeFirst

                    System.out.print("removeFirst("+i+")"+" "+LDeque.removeFirst());
                    System.out.println("");
                    break;
                }
                case 3:{
                    //removeLast
                    System.out.print("removeLast("+i+")"+" "+LDeque.removeLast());
                    System.out.println("");
                    break;
                }
                case 4:{
                    //size
                    ;
                    System.out.print("Size:"+LDeque.size());
                    System.out.println("");
                    break;
                }
                case 5:{
                    //print
                    LDeque.printDeque();
                    System.out.println("");
                    break;
                }
            }
        }

    }

    public void testArrayDequeRandomCall(){
        ArrayDeque<Integer> ADeque = new  ArrayDeque<>();

        int N = 100000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            switch(operationNumber)
            {
                case 0:
                {
                    //addFirst
                    ADeque.addFirst(i);
                    System.out.print("addFirst("+i+")");
                    System.out.println("");
                    break;
                }
                case 1:{
                    //addLast
                    ADeque.addLast(i);
                    System.out.print("addLast("+i+")");
                    System.out.println("");
                    break;
                }
                case 2:{
                    //removeFirst

                    System.out.print("removeFirst("+i+")"+" "+ADeque.removeFirst());
                    System.out.println("");
                    break;
                }
                case 3:{
                    //removeLast
                    System.out.print("removeLast("+i+")"+" "+ADeque.removeLast());
                    System.out.println("");
                    break;
                }
                case 4:{
                    //size
                    ;
                    System.out.print("Size:"+ADeque.size());
                    System.out.println("");
                    break;
                }
                case 5:{
                    //print
                    ADeque.printDeque();
                    System.out.println("");
                    break;
                }
            }
        }

    }


}
