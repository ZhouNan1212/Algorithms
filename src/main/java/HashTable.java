import java.util.*;

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

    private class BST<Key extends Comparable<Key>, Value> {
        private Node root;

        private class Node {
            private Key key;
            private Value value;
            private Node left, right;
            private int N; // 以该节点为根的子树中的节点总数

            private Node(Key key, Value value, int N) {
                this.key = key;
                this.value = value;
                this.N = N;
            }
        }

        private int size() {
            return size(root);
        }

        private int size(Node x) {
            if (x == null) return 0;
            else return x.N;
        }

        private Value get(Key key) {
            return get(root, key);
        }

        private Value get(Node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) return get(x.left, key);
            else if (cmp > 0) return get(x.right, key);
            else return x.value;
        }

        private void put(Key key, Value value) {
            root = put(root, key, value); // 查找key。找到则跟新它的值，否则为它新创建一个节点
        }

        private Node put(Node x, Key key, Value value) {
            if (x == null) return new Node(key, value, 1);
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = put(x.left, key, value);
            else if (cmp > 0) x.right = put(x.right, key, value);
            else x.value = value;
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        private Key min() {
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null) return x;
            return min(x.left);
        }

        private Key floor(Key key) {
            Node x = floor(root, key);
            if (x == null) return null;
            return x.key;
        }

        private Node floor(Node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;
            if (cmp < 0) return floor(x.left, key);
            Node t = floor(x.right, key);
            if (t != null) return t;
            else           return x;
        }

        private Key select(int k) {return select(root, k).key;}

        private Node select(Node x, int k) {
            // 返回排名为k的节点
            if (x == null) return null;
            int t = size(x.left);
            if      (t > k) return select(x.left, k);
            else if (t < k) return select(x.right, k - t - 1);
            else            return x;
        }

        private int rank(Key key) {return rank(key, root);}

        private int rank(Key key, Node x) {
            if (x == null) return 0;
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) return rank(key, x.left);
            else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
            else              return size(x.left);
        }

        private void deleteMin() {root = deleteMin(root);}

        private Node deleteMin(Node x) {
            if (x == null) return null;
            x.left = deleteMin(x.left);
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        private void delete(Key key) {root = delete(root, key);}

        private Node delete(Node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = delete(x.left, key);
            else if (cmp > 0) x.right = delete(x.right, key);
            else {
                if (x.right == null) return x.left;
                if (x.left == null) return x.right;
                Node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

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
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.peek());
        System.out.println(stack.get(1));

//        int a = new Integer(1);
//        int b = new Integer(1);



//        Random random = new Random();
//        for (int i = 0; i < 500; i++) {
//            int key = random.nextInt(100), value =  random.nextInt(10);
//            binarySearchST.put(key, value);
//            System.out.println(binarySearchST.get(key));
//        }

    }
}
