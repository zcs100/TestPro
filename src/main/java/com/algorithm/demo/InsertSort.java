package com.algorithm.demo;

/**
 * Created by szc on 17/9/18.
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] array = {12,3,8,1,29,47,33,9};
        InsertSort insertSort = new InsertSort();
        array = insertSort.insertSort(array);
        for(int temp : array){
            System.out.println(temp);
        }
    }

    public int[] insertSort(int[] array) {
        int temp = 0;
        int j = 0;
        for(int i=1;i<array.length;i++){
            temp = array[i];
            for(j=i;j>0&&temp<array[j-1];j--){
                array[j] = array[j - 1];
            }
            array[j] = temp;
        }
        return array;
    }

}
