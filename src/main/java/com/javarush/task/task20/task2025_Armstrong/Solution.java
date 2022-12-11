package com.javarush.task.task20.task2025_Armstrong;


import java.util.*;

/*
Алгоритмы-числа
*/

public class Solution {
    private static int currentDegree = 1;
    private static byte[] byteArray;
    private static long sumOfNumbersOfDegrees = 0;
    private static long maxNumber = Long.MAX_VALUE;
    public static TreeSet<Long> armstrongNumbers = new TreeSet<>();

    public static long[][] numbersOfDegrees = new long[10][21];
    public static void initDegrees() {
        for (int j = 0; j < 10; j ++) {
            numbersOfDegrees[j][1] = j;
        }
        for (int i = 2; i < 21; i++) {
            for (int j = 0; j < 10; j++) {
                numbersOfDegrees[j][i] = numbersOfDegrees [j][i - 1] * j;
            }
        }
    }

    public static void main(String[] args) {

        long a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(Long.MAX_VALUE)));
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);


    }

    public static long[] getNumbers(long N) {
        if (N > 0) armstrongNumbers.add(1L);
        maxNumber = N;
        initDegrees();
        int maxDegree = calculateDegree(N);
        while (currentDegree < maxDegree + 1) {
            initArray(currentDegree);
            while (decrementArray()) {
                decrementArray();
                //sumOfNumbersOfDegrees(byteArray, currentDegree);
            }
            currentDegree++;
        }
        Long[] resultLong = armstrongNumbers.toArray(new Long[armstrongNumbers.size()]);
        long[] result = new long[resultLong.length];
        for (int i = 0; i < resultLong.length; i++) {
            result[i] = resultLong[i];
        }
        return result;
    }

    //calculate count of digits in number
    private static int calculateDegree(long N) {
        long p = 10;
        for (int i = 1; i < 19; i++) {
            if (N < p) {
                return i;
            }
            p *= 10;
        }
        return 19;
    }
    //init Array
    public static void initArray (int degree) {
        byteArray = new byte[degree];
        for (int i = 0; i < degree; i++) {
            byteArray[i] = 9;
        }
    }
    // sum of degrees of numbers
    public static long sumOfNumbersOfDegrees(byte[] byteArray, int range) {
        sumOfNumbersOfDegrees = 0;
        long[] numberOfDegrees = new long[range];         
        for (int i = 0; i < range; i++) {
            switch (byteArray[i]) {
                case 0: numberOfDegrees[i]= numbersOfDegrees [0][range];
                        break;
                case 1: numberOfDegrees[i]= numbersOfDegrees [1][range];
                        break;
                case 2: numberOfDegrees[i]= numbersOfDegrees [2][range];
                        break;
                case 3: numberOfDegrees[i]= numbersOfDegrees [3][range];
                        break;
                case 4: numberOfDegrees[i]= numbersOfDegrees [4][range];
                        break;
                case 5: numberOfDegrees[i]= numbersOfDegrees [5][range];
                        break;
                case 6: numberOfDegrees[i]= numbersOfDegrees [6][range];
                        break;
                case 7: numberOfDegrees[i]= numbersOfDegrees [7][range];
                        break;
                case 8: numberOfDegrees[i]= numbersOfDegrees [8][range];
                        break;
                case 9: numberOfDegrees[i]= numbersOfDegrees [9][range];
                        break;
            }
            sumOfNumbersOfDegrees += numberOfDegrees[i];
        }
        return sumOfNumbersOfDegrees;
    }
    //iterate massive
    private static boolean decrementArray() {
        int index = 0;

        while (index < byteArray.length && byteArray[index] == 0) {
            index++;
        }

        if (index + 1 == byteArray.length && byteArray[index] == 1) {
            return false;
        }
        sumOfNumbersOfDegrees(byteArray, currentDegree);
        if (calculateDegree(sumOfNumbersOfDegrees) == currentDegree) {
            byte [] tempByte = sumToByteArray(sumOfNumbersOfDegrees);
            if ((Arrays.equals(tempByte, byteArray)) && (sumOfNumbersOfDegrees < maxNumber)) {
                armstrongNumbers.add(sumOfNumbersOfDegrees);
                //System.out.println(sumOfNumbersOfDegrees);
            }
        }
        Arrays.fill(byteArray, 0, index + 1, (byte) (byteArray[index] - 1));
        return true;
    }
    //init Temp Array from sum
    private static byte[
            ] sumToByteArray(Long sum) {
        int range = calculateDegree(sum);
        byte[] byteArray = new byte[range];
        int i = 0;
        while(sum > 0) {
            byteArray[i] = (byte) (sum % 10);
            sum = sum / 10;
            i++;
        }
        Arrays.sort(byteArray);
        return byteArray;
    }



}
