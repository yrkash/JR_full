package com.javarush.task.task39.task3912;

/*
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args)
    {
        int m[][]
                = { { 0, 1, 1, 0, 1 }, { 1, 1, 0, 1, 0 },
                { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 } };

        System.out.println(maxSquare(m));
    }

    public static int maxSquare(int[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;

        int S[][] = new int[2][column], max = 0;

        // set all elements of S to 0 first
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < column; j++) {
                S[i][j] = 0;
            }
        }

        // Construct the entries
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

                // Compute the entrie at the current
                // position
                int entry = matrix[i][j];
                if (entry != 0) {
                    if (j != 0) {
                        entry = 1
                                + Math.min(
                                S[1][j - 1],
                                Math.min(S[0][j - 1],

                                        S[1][j]));
                    }
                }

                // Save the last entrie and add the new one
                S[0][j] = S[1][j];
                S[1][j] = entry;

                // Keep track of the max square length
                max = Math.max(max, entry);
            }
        }
        return max * max;
    }
}
