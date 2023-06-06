package deque;



import java.util.Iterator;

public class ArrayDeque<T>implements  Deque<T>,Iterable<T>{
    //实现双端循环队列
    private T [] items;
    private int nextFirst;
    private int nextEnd;
    private int size;



    public  ArrayDeque(){
        items=(T [])new Object[8];
        size=0;
        nextFirst=7;
        nextEnd=0;
    }

    //更新数组的size值（size表示数组列表中，有效存储数据的单元个数）
    private void resize(int capacity){
        T[] a=(T[])new Object[capacity];
        //Java不允许建立泛型数组，只能先建立对象数组再转换为泛型


        System.arraycopy(items,0,a,0, nextEnd);
        System.arraycopy(items,nextEnd,a,nextEnd+(capacity-size), size-(nextEnd));
        nextFirst=nextEnd-1+(capacity-size);//nextEnd-1才是数组的最后一个位置，这样计算出来的新数组的nextFirst才是正确位置


        items=a;//让指向原数组的指针指向更新了size的数组
    }

    private void resize_remove(int capacity){
        T[] a=(T[])new Object[capacity];
        //Java不允许建立泛型数组，只能先建立对象数组再转换为泛型
        int sp=(nextFirst+1)%items.length;
        int dp_size;
        if(nextEnd>nextFirst)
        {
            dp_size=nextEnd-1-nextFirst;
        }
        else{
            dp_size= items.length-sp;
        }


        int dp=capacity-dp_size;

        int sp2=nextEnd-size+dp_size;
        int dp2=sp2;


        System.arraycopy(items,sp,a,dp, dp_size);
        if(nextEnd<=nextFirst)

        System.arraycopy(items,sp2,a,dp2, size-dp_size);
        items=a;//让指向原数组的指针指向更新了size的数组
        nextFirst=(dp-1);//nextEnd-1才是数组的最后一个位置，这样计算出来的新数组的nextFirst才是正确位置
        if(nextFirst<0)
            nextFirst=capacity-1;


        nextEnd=nextFirst;







    }
    @Override
    public void addFirst(T x){
        if(size==items.length){
            resize(size*2);
        }
        items[nextFirst]=x;
        size=size+1;
        nextFirst-=1;
        if(nextFirst<0)
        {
            nextFirst= items.length-1;
        }

    }
    @Override
    public void addLast(T x){
        if(size==items.length){
            resize(size*2);
        }
        items[nextEnd]=x;
        size=size+1;
        nextEnd+=1;
        if(nextEnd>=items.length)
        {
            nextEnd=nextEnd% items.length;
        }

    }
    @Override
    public T removeFirst(){
        if(isEmpty())
        {
            System.out.print("have no element");
            return null;
        }
        int remove=nextFirst+1;
        if(remove>= items.length)
        {
            remove=remove% items.length;
        }
        T i=items[remove];
        items[remove]=null;

        nextFirst=remove;
        size=size-1;
        if(size<(items.length/4)&&items.length>8){
            resize_remove(items.length/4);
        }


    return i;
    }
    @Override
    public T removeLast(){
        if(isEmpty())
        {
            System.out.print("have no element");
            return null;
        }
        int remove=nextEnd-1;
        if(remove<0)
        {
            remove=items.length-1;
        }
        T i=items[remove];
        items[remove]=null;

        nextEnd=remove;
        size=size-1;
        if(size<(items.length/4)&&items.length>8){
            resize_remove(items.length/4);
        }


        return i;
    }



    public T get(int i){
        int p=nextFirst;
        return items[(p+i+1)% items.length];
    }
    @Override
    public int size(){
        return size;
    }



private class arrayIterator<T> implements Iterator<T>{
    private int first=(nextFirst+1)%items.length;
    private int end=first-1;

    @Override
    public boolean hasNext(){

        if(items[first]!=null&&first!=end)
            return true;
        else
            return false;
    }
    @Override
    public T next(){
        if(hasNext()){

            T item=(T)items[first];
            first++;
            if(first>= items.length)
            {
                first=first%items.length;
                end=end+1;
            }
            if(end<0)
            {
                end=size-1;
            }

            return item;
        }

        else
            return null;
    }
}

    public Iterator<T> iterator(){
        Iterator<T> i=new arrayIterator<T>();
        return i;
    }

    public void printDeque(){
        Iterator<T> i=iterator();
       while(i.hasNext()){
           System.out.print(i.next()+" ");
       }
//        int i=0;
//        while(i< items.length)
//        {
//            System.out.print(items[i]+" ");
//            i++;
//        }
    }

    public boolean equals(Object o){
        if (o == null) {
            System.out.print("is null");
            return false;
        }
        LinkedListDeque<T> a;
        ArrayDeque<T> b;
        if (o instanceof LinkedListDeque) {
            a = (LinkedListDeque<T>) o;
            if(a.size()!=this.size()){
                return false;
            }

           int i=0;
            boolean w;
            int k;
            int l;
            while(i<size)
            {
              k=(int)a.get(i);
              l=(int)this.get(i);
                if(a.get(i).equals(this.get(i)))
                {
                    i=i+1;
                    continue;
                }

                else
                    return false;
            }
        }
        else if (o instanceof ArrayDeque) {
            b = (ArrayDeque<T>) o;


            if(b.size()!=size()){
                return false;
            }

          int i=0;

            while(i<size)
            {
                if(b.get(i).equals(this.get(i)))
                {
                    i=i+1;
                    continue;

                }

                else
                    return false;
            }
        }
        else{
            return false;
        }
        return true;
    }


    }





