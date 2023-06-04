package deque;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> Ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", Ad1.isEmpty());
        Ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, Ad1.size());
        assertFalse("lld1 should now contain 1 item", Ad1.isEmpty());

        Ad1.addLast("middle");
        assertEquals(2, Ad1.size());

        Ad1.addLast("back");
        assertEquals(3, Ad1.size());

        System.out.println("Printing out deque: ");
        Ad1.printDeque();

    }

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void getTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> Ad1 = new ArrayDeque<String>();

        Ad1.addLast("a");
        Ad1.addLast("b");
        Ad1.addFirst("c");
        Ad1.addLast("d");
        Ad1.addLast("e");
        Ad1.addFirst("f");
        Ad1.addLast("g");
        Ad1.addLast("h");

        String str1=Ad1.getFirst();
        String str2=Ad1.getLast();

        System.out.println("Printing out deque: ");
        Ad1.printDeque();
        assertEquals("f",str1);
        assertEquals("h",str2);



    }
    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void removeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> Ad1 = new ArrayDeque<String>();

        Ad1.addLast("a");
        Ad1.addLast("b");
        Ad1.addFirst("c");
        Ad1.addLast("d");
        Ad1.addLast("e");
        Ad1.addFirst("f");
        Ad1.addLast("g");
        Ad1.addLast("h");
        System.out.println("Printing out deque: ");
        Ad1.printDeque();

        String str1=Ad1.removeFirst();

        String str2=Ad1.removeFirst();
        String str3=Ad1.removeLast();
        String str4=Ad1.removeFirst();

        String str5=Ad1.removeFirst();
        String str6=Ad1.removeLast();

        String str7=Ad1.removeFirst();
        String str8=Ad1.removeLast();



        assertEquals("f",str1);
        assertEquals("c",str2);
        assertEquals("h",str3);
        assertEquals("a",str4);
        assertEquals("b",str5);
        assertEquals("g",str6);
        assertEquals("d",str7);
        assertEquals("e",str8);
        System.out.println("");
        System.out.println("Printing out deque: ");
        Ad1.printDeque();



    }

    @Test
    public void resizeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<String> Ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", Ad1.isEmpty());
        Ad1.addLast("a");
        Ad1.addLast("b");
        Ad1.addFirst("c");
        Ad1.addLast("d");
        Ad1.addLast("e");
        Ad1.addFirst("f");
        Ad1.addLast("g");
        Ad1.addLast("h");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.




        System.out.println("Printing out deque: ");
        Ad1.printDeque();
        System.out.println("");
        Ad1.addLast("Z");
        Ad1.printDeque();

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            Ad1.addLast(i);
        }
        System.out.println("Printing out deque: ");
        Ad1.printDeque();
        for (int i = 0; i < 50; i++) {
            int j=Ad1.getFirst();
            assertEquals("Should have the same value", j, Ad1.removeFirst(), 0.0);
        }

        for (int i = Ad1.size()-1; i > 50; i--) {
            int j=Ad1.getLast();
            assertEquals("Should have the same value", j, Ad1.removeLast(), 0.0);
        }


    }
}
