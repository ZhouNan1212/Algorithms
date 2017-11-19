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

    public static void main(String[] args){
        Integer[] a = new Integer[3];
        a[0] = 1;
        a[1] = 0;
        a[2] = 3;
        Insertion(a);
        assert isSorted(a);
        show(a);
    }


}
