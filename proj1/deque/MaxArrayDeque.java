package deque;

import java.util.Comparator;

public class MaxArrayDeque<T>extends ArrayDeque<T> {
//利用继承ArrayDeque，简化MaxArrayDeque的代码，只需要使用ArrayDeque 的get 方法获取每一个元素来进行比较，得到最大值即可

    private ArrayDeque<T> array;
    private Comparator<T> c;



    public MaxArrayDeque(Comparator<T> a){
       super();
       c=a;

    }



    public T max(){
        int max_index=0;
      int i=0;
        while(i!=super.size())
        {
            if(c.compare(super.get(max_index),super.get(i))>0)
            {
                i=i+1;
                continue;
            }
            else{

                max_index=i;
                i=i+1;
            }


        }
        return super.get(max_index);
    }

    public T max(Comparator<T> comparator){
        int max_index=0;
        int i=0;
        while(i!=super.size())
        {
            if(comparator.compare(super.get(max_index),super.get(i))>0)
            {
                i=i+1;
                continue;
            }
            else{
                max_index=i;
                i=i+1;
            }


        }
        return super.get(max_index);
    }

}
