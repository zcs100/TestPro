package com.algorithm.demo;

/**
 * Created by szc on 17/9/18.
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        //int[] array = {12,3,8,1,29,47,33,9};
        int[] array = {1,3,8,9,12,29,33,47};
        BubbleSort bubbleSort = new BubbleSort();
        array = bubbleSort.bubbleSort1(array);
        for(int temp : array){
            System.out.println(temp);
        }
    }

    /**
     * 上面的优化
     * @param array
     * 时间复杂度 n
     */
    public int[] bubbleSort1(int[] array) {
        boolean swap = false;
        for (int i = 0; i < array.length; i++)
        {
            swap = false;
            for (int j = 0; j < array.length - 1 - i; j++){
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
        return array;
    }

    /**
     *
     * @param array
     * @return
     * 时间复杂度 n^2
     */
    public int[] bubbleSort(int[] array){
        for(int i = 0; i<array.length-1;i++){
            for(int j = 0; j< array.length-i-1;j++){
                if(array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        return array;
    }


}
