package hashmap;

import java.security.Key;
import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */





    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    private K key_null;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets=createTable(16);


        size=0;
        loadFactor=0.75;
    }

    public MyHashMap(int initialSize) {


        buckets=createTable(initialSize);
        size=0;


    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
       buckets=createTable(initialSize);

        size=0;
        loadFactor=maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        Node n=new Node(key,value);

        return n;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        //在这里实现链表的hashtable
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection[] b=new Collection[tableSize];//不支持泛型数组，因此应当先建立一个集合数组，再往里面填充桶
        for(int i=0;i<tableSize;i++){
            b[i]=createBucket();
        }

        return b;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private void resize(int length){
        Collection<Node>[] buckets_new=createTable(length);


        for(int i=0;i<buckets.length;i++){
            Node n=new Node(null,null);
            Iterator<Node> ite=buckets[i].iterator();
            while(ite.hasNext())
            {
                n=ite.next();
                int hashindex = n.key.hashCode()% length;
                if(hashindex>=0)
                buckets_new[hashindex].add(n);
            }




        }
        buckets=buckets_new;
        loadFactor=size / buckets.length;
    }

    @Override
    public V get(K key) {
        Node n = new Node(key,null);
        int hashindex = key.hashCode()% buckets.length;
        if(hashindex<0)
            hashindex=0;
        Iterator<Node> i = buckets[hashindex].iterator();
        while(i.hasNext()){
            n = i.next();
            if(n.key.equals(key))
                break;
        }

        V v=n.value;
        return v;
    }

    @Override
    public void put(K key, V value) {
        int hashindex = key.hashCode()% buckets.length;
        if(hashindex<0)
            hashindex=0;
        if(buckets[hashindex] == null){
            buckets[hashindex]=createBucket();
        }
        double lf=size / buckets.length;
        if(lf>loadFactor){
            resize(buckets.length*2);
            hashindex = key.hashCode()% buckets.length;
        }

        Iterator<Node> ite=buckets[hashindex].iterator();
        while(ite.hasNext()){
            Node n1=ite.next();
            if(n1.key.equals(key))
            {
                buckets[hashindex].remove(n1);
                Node n=new Node(key,value);
                buckets[hashindex].add(n);
                return ;
            }
        }

        Node n=new Node(key,value);
        buckets[hashindex].add(n);
        size++;


    }

    @Override
    public Set<K> keySet() {
        Set<K> s=new HashSet<>();
        for(int i=0;i< buckets.length;i++){
            if(buckets[i]!=null){
                Node n=new Node(null,null);
                Iterator<Node> ite=buckets[i].iterator();
                while(ite.hasNext()){
                    s.add(ite.next().key);
                }
            }
        }

        return s;
    }

    @Override
    public boolean containsKey(K key) {
        int hashindex = key.hashCode()% buckets.length;

        if(hashindex<0)
            hashindex=0;

        if(buckets[hashindex]==null)
            return false;
        else if(buckets[hashindex]!=null){
            Iterator<Node> i = buckets[hashindex].iterator();
            while(i.hasNext()){
                Node n=i.next();
                if(n.key.equals(key))
                {
                    if(!n.key.equals(key_null)&&n.value!=null)
                    return true;
                    if(n.key.equals(key_null)&&n.value==null)
                        return true;
                    return false;
                }



            }
        }



        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(int i=0;i< buckets.length;i++){
            if(buckets[i]!=null){
                Iterator<Node> ite = buckets[i].iterator();
                while(ite.hasNext()){
                    ite.next().value=null;

                }
            }
        }
        size=0;
    }

    private class mapIterator implements Iterator<K>{
        private int index=0;
        private int ite_size=size;
        public boolean hasNext(){



            if(ite_size>0)
                return true;
            else
                return false;
        }

        @Override
        public K next() {


            while(index< buckets.length){
                if(buckets[index]!=null){
                    Node n=new Node(null,null);
                    Iterator<Node> ite=buckets[index].iterator();
                    if(ite.hasNext())
                    {
                        n=ite.next();
                        ite_size--;
                        return n.key;
                    }
                    else
                        index++;
                }
            }



            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new mapIterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
