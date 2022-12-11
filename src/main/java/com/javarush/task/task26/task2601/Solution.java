package com.javarush.task.task26.task2601;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/

public class Solution {

    public static void main(String[] args) {
        Integer[] array = {13, 8, 15, 5, 17};
        Solution.sort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

    }

    public static Integer[] sort(Integer[] array) {
        int length = array.length;
        //implement logic here
        Arrays.sort(array);
        Integer mediana = (length % 2 == 0) ? (array[length / 2] + array[(length - 1 )/ 2]) / 2 : array[length / 2];
        //System.out.println(mediana);
        Comparator<Integer> medianaSort = (o1, o2) -> Math.abs(o1 - mediana) - Math.abs(o2 - mediana);
        ArrayList <Integer> list = new ArrayList<Integer>(Arrays.asList(array));
        Collections.sort(list,medianaSort);
        //Integer[] result = new Integer[array.length];
        int i = 0;
        for (Integer element: list) {
            array[i] = element;
            i++;
        }
        return array;
    }


}
