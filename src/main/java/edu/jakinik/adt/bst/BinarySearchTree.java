package edu.jakinik.adt.bst;

public interface BinarySearchTree<T extends Comparable<T>> {

    BinarySearchTree<T> insert(T toInsert);

    BinarySearchTree<T> remove(T toRemove);

    boolean contains(T toCheck);

    T min();

    T max();
}
