import java.util.Random;

public class PriorityQueue {
    private static class maxPriorityQueue<Key extends Comparable<Key>> {
        private Key[] priorityQueue; // 基于堆的完全二叉树

        private int N = 0;  // 存储于priorityQueue[1...N]中，priorityQueue[0]没有使用

        private maxPriorityQueue(int maxN) {
            priorityQueue = (Key[]) new Comparable[maxN + 1];
        }

        private boolean isEmpty() { return N == 0;}

        private int size() {return N;}

        private void insert(Key key) {
            priorityQueue[++N] = key;
            swim(N);
        }

        private Key deleteMax() {
            Key max = priorityQueue[1];  //从根节点得到最大元素
            exch(1, N--);              //将其和最后一个节点交换
            priorityQueue[N + 1] = null; //防止对象游离
            sink(1);                   //恢复堆的有序性
            return max;
        }

        private boolean less(int i, int j) {return priorityQueue[i].compareTo(priorityQueue[j]) < 0; }

        private void exch(int i, int j) {Key t = priorityQueue[i]; priorityQueue[i] = priorityQueue[j]; priorityQueue[j] = t;}

        private void swim(int k) {
            while (k > 1 && less(k / 2, k)) {
                exch(k / 2, k);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && less(j, j + 1)) j++;
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
            }
        }
    }

    public static void main(String[] args){
        maxPriorityQueue<Integer> maxPriorityQueue = new maxPriorityQueue<>(100);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            maxPriorityQueue.insert(random.nextInt(100));
        }
        while (maxPriorityQueue.size() > 0) {
            System.out.println(maxPriorityQueue.deleteMax());
        }
    }
}
