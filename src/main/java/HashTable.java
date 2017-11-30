import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class HashTable {
    private static class SequentialSearchST<Key, Value> {  // 无序链表的顺序查找
        private Node first; //链表首节点

        private class Node { //链表节点的定义
            Key key;

            Value value;

            Node next;

            private Node(Key key, Value value, Node next) {
                this.key = key;

                this.value = value;

                this.next = next;
            }
        }

        private Value get(Key key) { //查找给定的键，返回相关联的值
            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key)) return x.value; //命中
            }
            return null; //未命中
        }

        private void put(Key key, Value value) { //查找给定的键值，找到则更新其值，否则新建节点
            for (Node x = first; x != null; x = x.next){
                if (key.equals(x.key)) { x.value = value; return; } //命中，更新
            }
            first = new Node(key, value, first); //未命中，新建节点
        }

        private ArrayList<Key> keys() {
            ArrayList<Key> keys = new ArrayList<>();
            for (Node x = first; x != null; x = x.next) {
                keys.add(x.key);
            }
            return keys;
        }

    }

    private static class BinarySearchST<Key extends Comparable<Key>, Value> {
        private Key[] keys;
        private Value[] values;
        private int N;

        private BinarySearchST(int capacity) {
            keys = (Key[]) new Comparable[capacity];
            values = (Value[]) new Comparable[capacity];
        }

        private int size() {return N;}

        private boolean isEmpty(){
            return N == 0;
        }

        private Value get(Key key) {
            if (isEmpty()) return null;
            int i = rank(key);
            if (i < N && keys[i].compareTo(key) == 0) return values[i];
            else                                      return null;
        }

        private int rank(Key key) {
            int lo = 0, hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = key.compareTo(keys[mid]);
                if      (cmp < 0) hi = mid - 1;
                else if (cmp > 0) lo = mid + 1;
                else return mid;
            }
            return lo;
        }

        private void put(Key key, Value value) { //查找键，找到则跟新键值，否则创建新元素
            int i = rank(key);
            if (i < N && keys[i].compareTo(key) == 0) { values[i] = value; return;}
            for (int j = N; j > i; j--) {
                keys[j] = keys[j - 1];
                values[i] = value;
            }
            keys[i] = key; values[i] = value;
            N++;
        }

        private Key min() {return keys[0];}

        private Key max() {return keys[N - 1];}

        private Key select(int k) {return keys[k];}

        private Key ceiling(Key key) {int i = rank(key); return keys[i];}

        private Iterable<Key> keys(Key lo, Key hi) {
            Queue<Key> q = new LinkedList<Key>();
            for (int i = rank(lo); i < rank(hi); i++) { q.add(keys[i]); }
            if (contains(hi)) q.add(keys[rank(hi)]);
            return q;
        }

        private boolean contains(Key hi) {  //判断是否包含键hi
            return get(hi) != null;
        }
    }

    private static class SeparateChainingHashST<Key, Value>{
        private int N; //键值对总数
        private int M; //散列表大小
        private SequentialSearchST<Key, Value>[] st; //存放链表对象的数组

        private SeparateChainingHashST() { this(997);}

        private SeparateChainingHashST(int M) {
            this.M = M;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
            for (int i = 0; i < M; i++) {
                st[i] = new SequentialSearchST();
            }
        }

        private int hash(Key key) {return ((key.hashCode() & 0x7fffffff) % M);}

        private Value get(Key key) {return (Value) st[hash(key)].get(key);}

        private void put(Key key, Value value) { st[hash(key)].put(key, value);}

    }

    private class LinearProbingHashST<Key, Value> {
        private int N;
        private int M = 16;
        private Key[] keys;
        private Value[] values;

        private LinearProbingHashST(){
            keys = (Key[]) new Object[M];
            values = (Value[]) new Object[M];
        }

        private int hash(Key key) {return (key.hashCode() & 0xfffffff) % M; }

        private void resize() {}
    }

    public static void main(String[] args){
        BinarySearchST binarySearchST = new BinarySearchST(100);

        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            int key = random.nextInt(100), value =  random.nextInt(10);
            binarySearchST.put(key, value);
            System.out.println(binarySearchST.get(key));
        }

    }
}
