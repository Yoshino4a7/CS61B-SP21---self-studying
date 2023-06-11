package bstmap;

import org.junit.Test;

import java.util.Iterator;

public class iteratorTest {
    @Test
    public void printTest(){
        BSTMap<Integer,String> map=new BSTMap<Integer,String>();
        map.put(4,"D");
        map.put(1,"A");
        map.put(3,"C");
        map.put(2,"B");
        map.put(5,"E");

        Iterator i=map.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }
}
