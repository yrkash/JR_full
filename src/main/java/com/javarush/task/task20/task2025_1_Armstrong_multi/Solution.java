package com.javarush.task.task20.task2025_1_Armstrong_multi;


import java.util.*;

/*
Алгоритмы-числа
*/

public class Solution {
    private static int currentDegree = 1;

    private static long maxNumber = Long.MAX_VALUE;
    public volatile static TreeSet<Long> armstrongNumbers = new TreeSet<>();

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

    public static void main(String[] args) throws InterruptedException {

        long a = System.currentTimeMillis();
        System.out.println(Arrays.toString(getNumbers(Long.MAX_VALUE)));
        long b = System.currentTimeMillis();
        System.out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        System.out.println("time = " + (b - a) / 1000);


    }

    public static long[] getNumbers(long N) throws InterruptedException {
        if (N > 0) armstrongNumbers.add(1L);
        maxNumber = N;
        initDegrees();
        //int maxDegree = calculateDegree(N);
        CalcNumbers calc1 = new CalcNumbers(1,10);
        CalcNumbers calc2 = new CalcNumbers(11,15);
        CalcNumbers calc3 = new CalcNumbers(16,17);
        CalcNumbers calc4 = new CalcNumbers(18,18);
        CalcNumbers calc5 = new CalcNumbers(19,19);
        calc1.start();
        calc2.start();
        calc3.start();
        calc4.start();
        calc5.start();
        calc1.join();
        calc2.join();
        calc3.join();
        calc4.join();
        calc5.join();
        Long[] resultLong = armstrongNumbers.toArray(new Long[armstrongNumbers.size()]);
        long[] result = new long[resultLong.length];
        for (int i = 0; i < resultLong.length; i++) {
            result[i] = resultLong[i];
        }
        return result;
    }

    //calculate count of digits in number


    static class CalcNumbers extends Thread {
        private int minDegree;
        private int maxDegree;
        private byte[] byteArray;
        private long sumOfNumbersOfDegrees = 0;

        public CalcNumbers(int minDegree, int maxDegree) {
            this.minDegree = minDegree;
            this.maxDegree = maxDegree;
            //this.run();
        }
        @Override
        public void run() {
            System.out.println(this.getClass().getSimpleName() + "started...");
            int curDegree = this.minDegree;
            while (curDegree < this.maxDegree + 1) {
                this.initArray(curDegree);
                while (this.decrementArray(curDegree)) {
                    this.decrementArray(curDegree);
                    //sumOfNumbersOfDegrees(byteArray, currentDegree);
                }
                curDegree++;
            }
        }
        //init Array
        public  void initArray (int degree) {
            byteArray = new byte[degree];
            System.out.printf("Init array size %d%n", degree);
            for (int i = 0; i < degree; i++) {
                byteArray[i] = 9;
            }
        }

        private int calculateDegree(long N) {
            long p = 10;
            for (int i = 1; i < 19; i++) {
                if (N < p) {
                    return i;
                }
                p *= 10;
            }
            return 19;
        }

        // sum of degrees of numbers
        public long sumOfNumbersOfDegrees(byte[] byteArray, int range) {
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
        private boolean decrementArray(int curDegree) {
            int index = 0;

            while (index < byteArray.length && byteArray[index] == 0) {
                index++;
            }

            if (index + 1 == byteArray.length && byteArray[index] == 1) {
                return false;
            }
            sumOfNumbersOfDegrees(byteArray, curDegree);
            if (calculateDegree(sumOfNumbersOfDegrees) == curDegree) {
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
        private byte[] sumToByteArray(Long sum) {
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

}
