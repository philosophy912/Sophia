package com.liner;

import java.util.Arrays;

/**
 * 数据结构之可变数组
 */
public class MyArray {
    private int[] elements;

    /**
     * 获取指定下标的数组内容
     *
     * @param index
     * @return
     */
    public int get(int index) {
        if (index >= 0 && index < elements.length) {
            return elements[index];
        }
        return -1;
    }

    /**
     * 在数组的末尾增加内容
     *
     * @param dst
     */
    public void add(int dst) {
        int[] temp = new int[elements.length + 1];
        for (int i = 0; i < elements.length; i++) {
            temp[i] = elements[i];
        }
        temp[elements.length + 1] = dst;
        elements = temp;
    }

    /**
     * 删除数组中指定位置的内容
     *
     * @param index
     */
    public void delete(int index) {
        if (index >= 0 && index < elements.length) {
            int[] temp = new int[elements.length - 1];
            for (int i = 0; i < elements.length; i++) {
                if (i <= index) {
                    temp[i] = elements[i];
                } else {
                    temp[i] = elements[i + 1];
                }
            }
            elements = temp;
        }
    }

    /**
     * 插入数字到指定的位置
     *
     * @param dst
     * @param index
     */
    public void insert(int dst, int index) {
        if(index >=0 && index <elements.length){
            int[] temp = new int[elements.length+1];
            for(int i=0;i<elements.length;i++){
                if(i<index){
                    temp[i] = elements[i];
                }else{
                    temp[i+1] = elements[i];
                }
            }
            temp[index] = dst;
            elements = temp;
        }
    }

    /**
     * 打印数组的内容
     */
    public void show() {
        System.out.println(Arrays.toString(elements));
    }

    /**
     * 查找内容在数组中的位置
     *
     * @param dst
     * @return
     */
    public int search(int dst) {
        for(int i=0;i<elements.length;i++){
            if(elements[i] == dst){
                return i;
            }
        }
        return -1;
    }

    /**
     * 二分法查找内容在数组中的位置
     * 思路： 首先找到数组中间的位置，然后比较要查找的数和中间位置的数字，
     * 如果查找的数字比中间的数字打，则继续向后二分法查找，如果小则向前查找
     * <p>
     * 该方法只适用于有序数组
     *
     * @param dst
     * @return
     */
    public int binarySearch(int dst) {
        return 0;
    }
}
