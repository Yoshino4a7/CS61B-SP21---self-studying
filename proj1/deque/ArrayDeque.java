package deque;



import java.util.Iterator;

public class ArrayDeque<Item>implements Deque<Item> {
    //实现双端循环队列
    private Item [] items;
    private int nextFirst;
    private int nextEnd;
    private int size;

    public  ArrayDeque(){
        items=(Item [])new Object[8];
        size=0;
        nextFirst=7;
        nextEnd=0;
    }
    public ArrayDeque(int capacity){
        items=(Item [])new Object[capacity];
        nextFirst=capacity-1;

        nextEnd=0;
        size=0;

    }
    //更新数组的size值（size表示数组列表中，有效存储数据的单元个数）
    private void resize(int capacity){
        Item[] a=(Item[])new Object[capacity];
        //Java不允许建立泛型数组，只能先建立对象数组再转换为泛型


        System.arraycopy(items,0,a,0, nextEnd);
        System.arraycopy(items,nextEnd,a,nextEnd+(capacity-size), size-(nextEnd));
        nextFirst=nextEnd-1+(capacity-size);//nextEnd-1才是数组的最后一个位置，这样计算出来的新数组的nextFirst才是正确位置


        items=a;//让指向原数组的指针指向更新了size的数组
    }
    @Override
    public void addFirst(Item x){
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
    public void addLast(Item x){
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
    public Item removeFirst(){
        if(isEmpty())
        {
            System.out.print("have no element");
            return null;
        }
        if(size<=items.length/4&&items.length>8){
            resize(items.length/4);
        }
        int remove=nextFirst+1;
        if(remove>= items.length)
        {
            remove=remove% items.length;
        }
        Item i=items[remove];
        items[remove]=null;
        size=size-1;
        nextFirst=remove;

    return i;
    }
    @Override
    public Item removeLast(){
        if(isEmpty())
        {
            System.out.print("have no element");
            return null;
        }
        if(size<=items.length/4&&items.length>8){
            resize(items.length/4);
        }
        int remove=nextEnd-1;
        if(remove<0)
        {
            remove=items.length-1;
        }
        Item i=items[remove];
        items[remove]=null;
        size=size-1;
        nextEnd=remove;

        return i;
    }

    public Item getLast(){
        int last=nextEnd-1;
        if(last<0)
        {
            last=0;
        }
        return items[last];
    }
    public Item getFirst(){
        int first=nextFirst+1;
        if(first>=items.length)
        {
            first=first%items.length;
        }
        return items[first];
    }

    public Item get(int i){
        int p=nextFirst;
        return items[(p+i)% items.length];
    }
    @Override
    public int size(){
        return size;
    }
   @Override
    public void printDeque(){
//        Iterator<Item> i=iterator();
//       while(i.hasNext()){
//           System.out.print(i.next()+" ");
//       }
       int i=0;
       while(i< items.length)
       {
           System.out.print(items[i]+" ");
           i++;
       }
    }
    public Iterator<Item> iterator(){
        Iterator<Item> i=new Iterator<Item>() {
            private int first=(nextFirst+1)%items.length;
            private int end=first-1;

            @Override
            public boolean hasNext() {



                if(items[first]!=null&&first!=end)
                    return true;
                else
                    return false;
            }

            @Override
            public Item next() {
                if(hasNext()){

                    Item item=items[first];
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
        };
        return i;
    }

    }



