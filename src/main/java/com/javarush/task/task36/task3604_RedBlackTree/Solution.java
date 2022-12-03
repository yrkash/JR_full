package com.javarush.task.task36.task3604_RedBlackTree;

public class Solution {

    public static void main(String[] args) {
        final RedBlackTree tree = new RedBlackTree();


        tree.insert(1);
        tree.insert(0);
        tree.insert(4);
        tree.insert(3);
        tree.insert(2);
        tree.insert(5);
        tree.insert(0);
        new BinaryTreePrinter(tree.getHeader()).print(System.out);
    }

}