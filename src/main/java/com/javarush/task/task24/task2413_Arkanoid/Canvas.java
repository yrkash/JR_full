package com.javarush.task.task24.task2413_Arkanoid;

public class Canvas {

    private int width;
    private int height;
    private char[][] matrix;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new char[height + 2][width + 2];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public void setPoint(double x, double y, char c) {
        int _x = (int) Math.round(x);
        int _y = (int) Math.round(y);

        if ((_x < 0) || (_x >= matrix[0].length) || (_y < 0) || ( _y >= matrix.length)) {

        } else {
            matrix[_y][_x] = c;
        }
    }

    public void drawMatrix(double x, double y, int[][] matrix, char c) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j =0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0)  {
                    this.setPoint(x + j, y + i, c);
                }
            }
        }
    }

    public void clear() {
        this.setMatrix(new char[this.height + 2][this.width + 2]);
    }

    public void print(){
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

}
