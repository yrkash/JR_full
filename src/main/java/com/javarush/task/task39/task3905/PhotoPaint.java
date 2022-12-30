package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        int row = image.length;
        int column = image[0].length;
        if (x > (column - 1) || y > (row - 1)) return false;
        if (image[y][x].equals(desiredColor)) return false;
        image[y][x] = desiredColor;
        return true;
    }
}
