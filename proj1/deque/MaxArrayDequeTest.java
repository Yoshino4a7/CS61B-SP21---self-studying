package deque;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {

    @Test
    public void testMax(){
        Comparator<Integer> c=new maxComparator();
        MaxArrayDeque<Integer> md1=new MaxArrayDeque<Integer>(c);


        md1.addLast(1);
        md1.addLast(2);
        md1.addLast(3);
        md1.addLast(4);
        md1.addLast(5);
        md1.addLast(6);
        md1.addLast(7);
        md1.addLast(8);
        md1.addLast(9);
        md1.addLast(10);

        md1.addLast(11);
        md1.addLast(12);
        md1.addLast(13);
        md1.addLast(14);
        md1.addLast(15);
        md1.addLast(16);
        md1.addLast(17);
        md1.addLast(18);
        md1.addLast(19);
        md1.addLast(20);

        int a=md1.max();
        assertEquals(20,a);

        MaxArrayDeque<Integer> md2=new MaxArrayDeque<Integer>(c);

        md2.addLast(1);
        md2.addLast(2);
        md2.addLast(3);
        md2.addLast(4);
        md2.addLast(5);
        md2.addLast(6);
        md2.addLast(7);
        md2.addLast(8);
        md2.addLast(9);
        md2.addLast(15);
        int b=md2.max(c);
        assertEquals(15,b);


        MaxArrayDeque<Integer> md3=new MaxArrayDeque<Integer>(c);


        md3.addLast(1);
        md3.addLast(2);
        md3.addLast(3);
        md3.addLast(4);
        md3.addLast(5);
        md3.addLast(6);
        md3.addLast(7);
        md3.addLast(8);
        md3.addLast(9);
        md3.addLast(10);

        md3.addFirst(11);
        md3.addFirst(12);
        md3.addFirst(13);
        md3.addFirst(14);
        md3.addFirst(15);
        md3.addFirst(16);
        md3.addFirst(17);
        md3.addFirst(18);
        md3.addFirst(19);
        md3.addFirst(30);
        int d=md3.max();

        assertEquals(30,d);
    }

//    public void testStringMax(){
//        Comparator<Integer> c=new maxComparator();
//        MaxArrayDeque<Integer> md1=new MaxArrayDeque<Integer>(c);
//
//
//        md1.addLast(1);
//        md1.addLast(2);
//        md1.addLast(3);
//        md1.addLast(4);
//        md1.addLast(5);
//        md1.addLast(6);
//        md1.addLast(7);
//        md1.addLast(8);
//        md1.addLast(9);
//        md1.addLast(10);
//
//        md1.addLast(11);
//        md1.addLast(12);
//        md1.addLast(13);
//        md1.addLast(14);
//        md1.addLast(15);
//        md1.addLast(16);
//        md1.addLast(17);
//        md1.addLast(18);
//        md1.addLast(19);
//        md1.addLast(20);
//
//        int a=md1.max();
//        assertEquals(20,a);
//
//        MaxArrayDeque<Integer> md2=new MaxArrayDeque<Integer>(c);
//
//        md2.addLast(1);
//        md2.addLast(2);
//        md2.addLast(3);
//        md2.addLast(4);
//        md2.addLast(5);
//        md2.addLast(6);
//        md2.addLast(7);
//        md2.addLast(8);
//        md2.addLast(9);
//        md2.addLast(15);
//        int b=md2.max(c);
//        assertEquals(15,b);
//
//
//        MaxArrayDeque<Integer> md3=new MaxArrayDeque<Integer>(c);
//
//
//        md3.addLast(1);
//        md3.addLast(2);
//        md3.addLast(3);
//        md3.addLast(4);
//        md3.addLast(5);
//        md3.addLast(6);
//        md3.addLast(7);
//        md3.addLast(8);
//        md3.addLast(9);
//        md3.addLast(10);
//
//        md3.addFirst(11);
//        md3.addFirst(12);
//        md3.addFirst(13);
//        md3.addFirst(14);
//        md3.addFirst(15);
//        md3.addFirst(16);
//        md3.addFirst(17);
//        md3.addFirst(18);
//        md3.addFirst(19);
//        md3.addFirst(30);
//        int d=md3.max();
//
//        assertEquals(30,d);
//    }
}
