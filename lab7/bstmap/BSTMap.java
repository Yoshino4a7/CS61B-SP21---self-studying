package bstmap;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.sun.tools.doclets.internal.toolkit.NestedClassWriter;
import com.sun.tools.hat.internal.model.Root;

import java.security.Key;
import java.util.Comparator;
import java.util.IllegalFormatCodePointException;
import java.util.Iterator;
import java.util.Set;

//对泛型K继承Comparable的有效范围只在BSTMap类里，只有在BSTMap类里K才能使用compareTo方法
public class BSTMap<K extends Comparable<K>,V > implements Map61B {




    private class BSTnode{
        private K key;
        private V item;
        private BSTnode left;
        private BSTnode right;

        BSTnode(K k,V i) {
            item = i;
            key=k;
            left = null;
            right = null;
        }


    }
    private BSTnode add(BSTnode T,K k,V i) {
        if (T == null)
        {

            return new BSTnode(k,i);
        }

        int cmp=k.compareTo(T.key);
        if (cmp<0 ) {
            T.left = add(T.left,k,i);
        }
        if (cmp>0)
            T.right = add(T.right,k,i);
        return T;

    }

    private BSTnode root;
    private int size=0;






    private void insert(BSTnode T,K k,V i) {
        root=add(T,k,i);
        size++;
    }

    private void setroot(BSTnode p) {
        root = p;
    }

    private void printTree() {
        preOrder(root);
    }

    private K find(BSTnode T,K key){
       BSTnode p=T;
       while(p.left!=null||p.right!=null){
           int cmp=key.compareTo(p.key);

           if(cmp>0)
            p=p.right;
           if(cmp<0)
            p=p.left;
           if(cmp==0)
               return p.key;
       }
        int cmp=key.compareTo(p.key);
        if(cmp==0)
            return p.key;
       return null;
    }

    private Object[] find_get(BSTnode T,K key){
        BSTnode p=T;
        Object[] obj=new Object[2];
        if(p==null)
            return obj;
        while(p.left!=null||p.right!=null){
            int cmp=key.compareTo(p.key);

            if(cmp>0)
                p=p.right;
            if(cmp<0)
                p=p.left;
            if(cmp==0)
            {
                V i=p.item;
                K k=p.key;
                obj[0]=k;
                obj[1]=i;
                return obj;
            }


        }
        int cmp=key.compareTo(p.key);
        if(cmp==0)
        {
            V i=p.item;
            K k=p.key;
            obj[0]=k;
            obj[1]=i;
            return obj;
        }

        return null;
    }


    private void preOrder(BSTnode p) {
        if (p.left != null) {
            preOrder(p.left);

        }
        System.out.println(p.item);
        if (p.right != null) {
            preOrder(p.right);
        }
    }
    private void preOrder_clear(BSTnode p) {
        if (p.left != null) {
            preOrder_clear(p.left);

        }
        p.item=null;
        if (p.right != null) {
            preOrder_clear(p.right);
        }
    }

    private BSTnode findSmallest(BSTnode T, BSTnode smallest) {

        if (T.left != null) {
            smallest = findSmallest(T.left, T.left);
            if (smallest.right != null)
                T.left = smallest.right;
            smallest.right = null;
        }
        if (smallest == null)
            return T;

        if (T.left == smallest)
            T.left = null;
        return smallest;


    }

    private BSTnode del(BSTnode T, K k) {
        if (T == null)
            return null;
        if (!k.equals(T.key)) {

            if (T.left == null && T.right == null) {
                return null;
            }

            if (T.left == null && T.right != null) {
                T = T.right;
                return T;
            }
            if (T.right == null && T.left != null) {
                T = T.left;
                return T;
            }
            if (T.right != null && T.left != null) {
                BSTnode T_left = T.left;
                BSTnode T_right = T.right;

                BSTnode smallnode = findSmallest(T.right, T.right.left);
                if (T_right == smallnode) {
                    T = smallnode;
                    T.left = T_left;
                    return T;
                }

                T = smallnode;
                T.right = T_right;
                T.left = T_left;
                return T;
            }


        }

        int cmp=k.compareTo(T.key);

        if (cmp<0) {
            T.left = del(T.left,k);
        }
        if (cmp>0)
            T.right = del(T.right,k);
        return T;

    }


    private void delete(K key) {
        root = del(root, key);
        size--;
    }


    @Override
    public void clear() {

      preOrder_clear(root);
      size=0;
    }

    @Override
    public boolean containsKey(Object key) {
        Object[] o=find_get(root,(K)key);

        if(o[0]==null)
        return false;
        else if(o[0]!=null&&o[1]==null&&size==0)
            return false;
        else if(o[0]!=null&&o[1]!=null)
            return true;
        return true;
    }

    @Override
    public Object get(Object key) {
        Object[] o=find_get(root,(K)key);
        return o[1];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(Object key, Object value) {
        K k=(K) key;
        V v=(V) value;

        insert(root,k,v);
    }

    @Override
    public Iterator iterator() {
        Iterator<K> i= new mapIterator();
        return i;
    }

    private class mapIterator implements Iterator<K>{
        private Object[] tree_key=new Object[size];
        private BSTnode cur;
        private int index=size-1;
        mapIterator(){
            int i=0;
            cur=root;
            while(i<size){
                if(cur.right!=null){
                    tree_key[i]=cur.right.key;
                    i++;
                }

                tree_key[i]=cur.key;
                cur=cur.left;
            }
        }

        public boolean hasNext(){
           return index!=0;
        }
        public K next(){
            K k=(K)tree_key[index];
            index--;
            return k;

        }
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}

