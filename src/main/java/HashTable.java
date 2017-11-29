import java.util.ArrayList;
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
        SeparateChainingHashST separateChainingHashST = new SeparateChainingHashST();

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int key = random.nextInt(100), value =  random.nextInt(10);
            separateChainingHashST.put(key, value);
            //System.out.println(separateChainingHashST.get(key));
        }

    }
}
