import java.util.Random;

public class Sort {
    private static boolean less(Comparable v, Comparable w) {return v.compareTo(w) < 0;}

    private static void exch(Comparable[] a, int i, int j) {Comparable t = a[i]; a[i] = a[j]; a[j] = t;}

    //打印数组
    private static void show(Comparable[] a){
        for (int i = 0; i < a.length; i++){
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    //测试数组是否有序
    private static boolean isSorted(Comparable[] a){
        for (int i = 1; i < a.length; i++){
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    //选择排序
    private static void Selection(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++){
            int min = i; // 把第一个没有排序过的元素设置为最小值
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) min = j;  // 如果元素 < 现在的最小值, 将此元素设置成为新的最小值
            }
            exch(a, i, min);  // 将最小值和第一个没有排序过的位置交换
        }
    }

    //插入排序
    private static void Insertion(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {  // 将a[i]插入到a[i - 1], a[i - 2],...之中
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    //希尔排序,也称缩小增量排序,
    //动画地址http://www.atool.org/sort.php
    private static void Shell(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3*h + 1;

        while (h >= 1) {  // 将数组变为h有序
            for (int i = h; i < N; i++){  // 将a[i]插入到a[i - h], a[i - 2h], a[i - 3h]...之中
                //System.out.println(i);
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    //System.out.println(h+"========"+i + "---------" + (j - h));
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }


    private static class Merge{
        private Comparable[] aux;  // 归并所需的辅助数组

        /*
         *原地归并的抽象方法
         */
        private void merge(Comparable[] a, int lo, int mid, int hi) {// 将a[lo...mid] 和a[mid+1...hi]归并
            int i = lo, j = mid + 1;
            System.arraycopy(a, lo, aux, lo, hi - lo + 1);
            //for (int k = lo; k <= hi; k++)
            //    aux[k] = a[k];
            /*
             * arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
             * src表示源数组，srcPos表示源数组要复制的起始位置，desc表示目标数组，length表示要复制的长度。
             */
            for (int k = lo; k <= hi; k++) {
                if      (i > mid)                a[k] = aux[j++];  // 左半边的用尽（取右半边元素）
                else if (j > hi )                a[k] = aux[i++];  // 右半边的用尽（取左半边元素）
                else if (less(aux[j], aux[i]))   a[k] = aux[j++];  // 右半边的当前元素小于左半边的当前元素（取右半边元素）
                else                             a[k] = aux[i++];  // 右半边的当前元素大于左半边的当前元素（取左半边元素）
            }
        }


        private void sort(Comparable[] a, int lo, int hi){  // 递归的形式
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, lo, mid);  // 将左半边排序
            sort(a, mid + 1, hi);  // 将右半边排序
            merge(a, lo, mid, hi);  // 归并结果
        }

        /*
         * 自顶向下的归并
         */
        private void upToDownMergeSort(Comparable[] a){
            aux = new Comparable[a.length];
            sort(a, 0, a.length - 1);
        }

        /*
         * 自底向上的归并
         */
        private void downToUpMergeSort(Comparable[] a){
            int length = a.length;
            aux = new Comparable[length];
            for (int sz = 1; sz < length; sz += sz){
                for (int lo = 0; lo < length - sz; lo += sz + sz) {
                    merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, length - 1));
                }
            }
        }


    }


    public static void main(String[] args){
        int len = 10;
        Integer[] a = new Integer[len];
        Random random = new Random();
        for (int x = 0; x < len; x++) {
           a[x] = random.nextInt(100);
           System.out.print(a[x] + ", ");
        }
        Merge merge = new Merge();
        merge.downToUpMergeSort(a);
        assert isSorted(a);
        System.out.println();
        show(a);
    }


}
