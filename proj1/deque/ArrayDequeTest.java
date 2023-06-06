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

        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> Ad2 = new ArrayDeque<Integer>();
        int get_num;

        for(int i=0;i<10000;i++)
        {
            Ad2.addLast(i);

        }
        for(int j=0;j<10000;j++)
        {
            get_num=Ad2.get(j);
            assertEquals(j,get_num);

        }

        Ad1.addFirst(1);
        Ad1.addFirst(2);
        Ad1.addFirst(3);
        Ad1.addFirst(4);
        Ad1.addFirst(5);
        Ad1.addFirst(6);
        Ad1.addFirst(7);
        Ad1.addFirst(8);
        Ad1.addFirst(9);
        Ad1.addFirst(10);
        Ad1.addFirst(11);
        Ad1.addFirst(12);
        Ad1.addLast(13);
        Ad1.addLast(14);
        Ad1.addLast(15);
        Ad1.addLast(16);
        Ad1.addLast(17);
        Ad1.addLast(18);

for(int i=0;i<12;i++){
    int str1=Ad1.get(i);
    assertEquals(12-i,str1);

}

        for(int i=12;i<17;i++){
            int str1=Ad1.get(i);
            assertEquals(i+1,str1);

        }



        System.out.println("Printing out deque: ");
        Ad1.printDeque();




    }
@Test
public void fillupandEmptyTest(){
    ArrayDeque<Integer> Ad2 = new ArrayDeque<Integer>();
    int get_num;
    for(int i=0;i<32;i++)
    {
        Ad2.addFirst(i);

    }
    for(int i=0;i<32;i++)
    {
        Ad2.removeLast();

    }



    for(int i=0;i<32;i++)
    {
        Ad2.addFirst(i);

    }
    for(int i=0;i<32;i++)
    {
        Ad2.removeLast();

    }
    
}
    @Test
    public void remove_BigTest(){
        ArrayDeque<Integer> Ad2 = new ArrayDeque<Integer>();
        int get_num;
        int get_num2;


        for(int i=0;i<1000000;i++)
        {
            Ad2.addLast(i);

        }
        for(int j=0;j<1000000;j++)
        {
            get_num=Ad2.get(j);
            assertEquals(j,get_num);

        }

        for(int i=0;i<1000000;i++)
        {

            get_num=Ad2.removeFirst();



        }



    }


    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void removeTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();

        Ad1.addLast(1);
        Ad1.addLast(2);
        Ad1.addLast(3);
        Ad1.addLast(4);
        Ad1.addLast(5);
        Ad1.addLast(6);
        Ad1.addLast(7);
        Ad1.addLast(8);



        Ad1.addFirst(9);
        Ad1.addFirst(10);
        Ad1.addFirst(11);
        Ad1.addFirst(12);
        Ad1.addFirst(13);
        Ad1.addFirst(14);
        Ad1.addFirst(15);
        Ad1.addFirst(16);

        System.out.println("Printing out deque: ");
        Ad1.printDeque();

        int str1=Ad1.removeFirst();
        int str2=Ad1.removeFirst();
        int str3=Ad1.removeFirst();
        int str4=Ad1.removeFirst();
        int str5=Ad1.removeFirst();
        int str6=Ad1.removeFirst();
        int str7=Ad1.removeFirst();
        int str8=Ad1.removeLast();

        int str9=Ad1.removeFirst();
        int str10=Ad1.removeFirst();
        int str11=Ad1.removeFirst();
        int str12=Ad1.removeFirst();
        int str13=Ad1.removeFirst();
        int str14=Ad1.removeFirst();
        int str15=Ad1.removeFirst();
        int str16=Ad1.removeLast();


        assertEquals(16,str1);
        assertEquals(15,str2);
        assertEquals(14,str3);
        assertEquals(13,str4);
        assertEquals(12,str5);
        assertEquals(11,str6);
        assertEquals(10,str7);
        assertEquals(8,str8);
        assertEquals(7,str16);
        assertEquals(9,str9);
        assertEquals(1,str10);
        assertEquals(2,str11);
        assertEquals(3,str12);
        assertEquals(4,str13);
        assertEquals(5,str14);
        assertEquals(6,str15);



        Ad1.addLast(21);
        Ad1.addLast(22);
        Ad1.addLast(23);
        Ad1.addLast(24);
        Ad1.addLast(25);
        Ad1.addLast(26);
        Ad1.addLast(27);
        Ad1.addLast(28);



        Ad1.addFirst(11);
        Ad1.addFirst(12);
        Ad1.addFirst(13);
        Ad1.addFirst(14);
        Ad1.addFirst(15);
        Ad1.addFirst(16);
        Ad1.addFirst(17);
        Ad1.addFirst(18);

        System.out.println("Printing out deque: ");
        Ad1.printDeque();


         str1=Ad1.removeFirst();
         str2=Ad1.removeFirst();
         str3=Ad1.removeFirst();
         str4=Ad1.removeFirst();
         str5=Ad1.removeFirst();
         str6=Ad1.removeFirst();
         str7=Ad1.removeFirst();
         str8=Ad1.removeLast();

         str9=Ad1.removeFirst();
         str10=Ad1.removeFirst();
         str11=Ad1.removeFirst();
         str12=Ad1.removeFirst();
         str13=Ad1.removeFirst();
         str14=Ad1.removeFirst();
         str15=Ad1.removeFirst();
         str16=Ad1.removeLast();

        assertEquals(18,str1);
        assertEquals(17,str2);
        assertEquals(16,str3);
        assertEquals(15,str4);
        assertEquals(14,str5);
        assertEquals(13,str6);
        assertEquals(12,str7);
        assertEquals(28,str8);
        assertEquals(27,str16);
        assertEquals(11,str9);
        assertEquals(21,str10);
        assertEquals(22,str11);
        assertEquals(23,str12);
        assertEquals(24,str13);
        assertEquals(25,str14);
        assertEquals(26,str15);

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
    public void equalsTest(){
        LinkedListDeque<Integer> Ld1 = new LinkedListDeque<Integer>();

        Ld1.addLast(1);
        Ld1.addLast(2);
        Ld1.addLast(3);
        Ld1.addLast(4);
        Ld1.addLast(5);
        Ld1.addLast(6);
        Ld1.addLast(7);
        Ld1.addLast(8);
        Ld1.addLast(9);
        Ld1.addLast(10);
        Ld1.addLast(11);
        Ld1.addLast(12);

        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();

        Ad1.addLast(1);
        Ad1.addLast(2);
        Ad1.addLast(3);
        Ad1.addLast(4);
        Ad1.addLast(5);
        Ad1.addLast(6);
        Ad1.addLast(7);
        Ad1.addLast(8);
        Ad1.addLast(9);
        Ad1.addLast(10);
        Ad1.addLast(11);
        Ad1.addLast(12);


        System.out.println("Printing out deque: ");
        Ad1.printDeque();
        System.out.println("Printing out deque: ");
        Ld1.printDeque();
        boolean b=Ad1.equals(Ld1);
        assertTrue("这两个列表应该相等",b);

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        ArrayDeque<Integer> Ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i <5000000; i++) {
            Ad1.addLast(i);
        }
//        System.out.println("Printing out deque: ");
//        Ad1.printDeque();
        for (int i = 0; i < 5000000; i++) {
            int j=Ad1.get(0);
            assertEquals("Should have the same value", j, Ad1.removeFirst(), 0.0);
        }


    }
}
