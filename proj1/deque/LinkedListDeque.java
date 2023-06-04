package deque;
import java.util.Iterator;

public class LinkedListDeque<T>implements Deque<T> {
    private IntNode sentFront;//前端哨兵
    private IntNode sentEnd;//后端哨兵
    private int size;
    private IntNode p_recur;

    private class IntNode {
        public T item;
        public IntNode next;
        public IntNode previous;

        public IntNode(T i, IntNode n,IntNode p) {
            item = i;
            next = n;
            previous=p;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */


    /** Creates an empty timingtest.SLList. */
    //该双端队列用链表实现，双端队列只需要有前哨兵和后哨兵以及size即可进行操作
    public LinkedListDeque() {
        sentFront = new IntNode(null, null,null);
        sentEnd = new IntNode(null, null,null);

        sentFront.next=sentEnd;
        sentEnd.previous=sentFront;

        p_recur=sentFront.next;

        size = 0;
    }

    public T getRecursive(int index){
        if(isEmpty())
            return null;

        if(index==0){
            T i=p_recur.item;
            p_recur=sentFront.next;
            return i;
        }
        else
        {
            p_recur=p_recur.next;

            System.out.print(p_recur.item+" ");
            return getRecursive(index-1);
        }

    }

    /** Adds x to the front of the list. *///在前端和后端插入元素，复杂度为1
    @Override
    public void addFirst(T x) {
        IntNode i=new IntNode(x, sentFront.next,sentFront);
        sentFront.next.previous=i;
        sentFront.next = i;
        size = size + 1;
        if(size==1)
        {
            sentEnd.previous=sentFront.next;
        }
        p_recur=sentFront.next;
    }
    @Override
    public void addLast(T x) {
        IntNode i=new IntNode(x, sentEnd,sentEnd.previous);
        sentEnd.previous.next=i;
        sentEnd.previous = i;

        size = size + 1;
        if(size==1)
        {
            sentFront.next=sentEnd.previous;
            p_recur=sentFront.next;
        }

    }

    //删除第一个元素
    @Override
    public T removeFirst() {
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

        p_recur=sentFront.next;
        return i.item;
    }
    //删除最后一个元素
    @Override
    public T removeLast() {
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


    public void printDeque()
    {
        Iterator<T> i=iterator();
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
    public T get(int index){
    if(isEmpty()) {
    System.out.print("不存在");
    return null;
    }else{
        T temp=null;
        IntNode p=sentFront.next;
        for(int i=0;i<index;i++){
           p=p.next;
        }
        temp=p.item;
        return temp;

    }
    }



    public Iterator<T> iterator(){
        Iterator<T> i=new Iterator<T>() {
            private IntNode p=sentFront.next;;
            @Override
            public boolean hasNext() {

                if(p!=null&&p!=sentEnd)
                return true;
                else
                    return false;
            }

            @Override
            public T next() {
                if(hasNext()&&p!=sentEnd){

                    T item=p.item;
                    p=p.next;
                    return item;
                }

                else return null;
            }
        };
        return i;
    }


    public boolean equals(Object o){
        if(o instanceof LinkedListDeque)//instanceof可以判断o对象是否为LinkedListDeque类
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
