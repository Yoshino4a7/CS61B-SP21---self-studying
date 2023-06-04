package deque;
import java.util.Iterator;

public class LinkedListDeque<Item>implements Deque<Item> {
    private class IntNode {
        public Item item;
        public IntNode next;
        public IntNode previous;

        public IntNode(Item i, IntNode n,IntNode p) {
            item = i;
            next = n;
            previous=p;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntNode sentFront;//前端哨兵
    private IntNode sentEnd;//后端哨兵
    private int size;

    /** Creates an empty timingtest.SLList. */
    //该双端队列用链表实现，双端队列只需要有前哨兵和后哨兵以及size即可进行操作
    public LinkedListDeque() {
        sentFront = new IntNode(null, null,null);
        sentEnd = new IntNode(null, null,null);

        sentFront.next=sentEnd;
        sentEnd.previous=sentFront;

        size = 0;
    }
    public LinkedListDeque(Item x) {
        sentFront = new IntNode(null, null,null);
        sentEnd = new IntNode(null, null,null);

        sentFront.next=sentEnd;
        sentEnd.previous=sentFront;
        IntNode i=new IntNode(x,sentEnd,sentFront);
        sentFront.next=i;
        sentEnd.previous=i;
        size = 1;

    }


    /** Adds x to the front of the list. *///在前端和后端插入元素，复杂度为1
    @Override
    public void addFirst(Item x) {
        IntNode i=new IntNode(x, sentFront.next,sentFront);
        sentFront.next.previous=i;
        sentFront.next = i;
        size = size + 1;
        if(size==1)
        {
            sentEnd.previous=sentFront.next;
        }
    }
    @Override
    public void addLast(Item x) {
        IntNode i=new IntNode(x, sentEnd,sentEnd.previous);
        sentEnd.previous.next=i;
        sentEnd.previous = i;
        size = size + 1;
        if(size==1)
        {
            sentFront.next=sentEnd.previous;
        }
    }

    //删除第一个元素
    @Override
    public Item removeFirst() {
        if(isEmpty())
        {
            return null;
        }
        IntNode i=sentFront.next;
        IntNode temp=sentFront.next.next;
        sentFront.next.next=null;
        sentFront.next.previous=null;
        sentFront.next=temp;
        temp.previous=sentFront;
        size=size-1;
        return i.item;
    }
    //删除最后一个元素
    @Override
    public Item removeLast() {
        if(isEmpty())
        {
            return null;
        }
        IntNode i=sentEnd.previous;
        IntNode temp=sentEnd.previous.previous;
        sentEnd.previous.next=null;
        sentEnd.previous.previous=null;
        sentEnd.previous=temp;
        temp.next=sentEnd;
        size=size-1;
        return i.item;

    }
    @Override
//    public void printDeque()
//    {
//        IntNode p=sentFront.next;
//        while(p!=sentEnd)
//        {
//            System.out.print(p.item+" ");
//            p=p.next;
//
//
//        }
//        //λ表达式，（s）为forEachRemaining函数遍历过程获得的每一个元素，将s作为参数传递到λ表达式中->指向的函数
//    }

    public void printDeque()
    {
        Iterator<Item> i=iterator();
        while(i.hasNext()){
            System.out.print(i.next()+" ");
        }


//        IntNode p=sentFront.next;
//        while(p!=sentEnd)
//        {
//            System.out.print(p.item+" ");
//            p=p.next;
//
//
//        }
        //λ表达式，（s）为forEachRemaining函数遍历过程获得的每一个元素，将s作为参数传递到λ表达式中->指向的函数
    }

    /** Returns the first item in the list. */
    public Item get(int index){
    if(isEmpty()) {
    System.out.print("不存在");
    return null;
    }else{
        Item temp=null;
        IntNode p=sentFront;
        for(int i=0;i<index;i++){
           p=p.next;
        }
        temp=p.item;
        return temp;

    }
    }

    public Item getFirst(){
        return sentFront.next.item;
    }

    public Item getEnd(){
        return sentEnd.previous.item;
    }

    public Iterator<Item> iterator(){
        Iterator<Item> i=new Iterator<Item>() {
            private IntNode p=sentFront.next;;
            @Override
            public boolean hasNext() {

                if(p!=null&&p!=sentEnd)
                return true;
                else
                    return false;
            }

            @Override
            public Item next() {
                if(hasNext()&&p!=sentEnd){

                    Item item=p.item;
                    p=p.next;
                    return item;
                }

                else return null;
            }
        };
        return i;
    }

//    public Iterator<Item> iterator(){
//        Iterator<Item> i=new Iterator<Item>() {
//            @Override
//            public boolean hasNext() {
//                if(size<=0)
//                return false;
//                else{
//                    return true;
//                }
//            }
//
//            @Override
//            public Item next() {
//                return sentFront.next.item;
//            }
//        };
//        return i;
//    }
    public boolean equals(Object o){
        if(o == this)//instanceof可以判断o对象是否为LinkedListDeque类
        {
            return true;
        }else{
            return false;
        }
    }

    /** Returns the size of the list. */
    @Override
    public int size() {
        return size;
    }


}
