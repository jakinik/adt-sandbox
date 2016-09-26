package edu.jakinik.adt.bst;

public class BinarySearchTreeImpl<T extends Comparable<T>> implements BinarySearchTree<T> {

    private final T element;

    private final BinarySearchTree<T> left;

    private final BinarySearchTree<T> right;

    public static <T extends Comparable<T>> BinarySearchTree<T> withRoot(T element) {
        return leaf(element);
    }

    @Override
    public BinarySearchTree<T> insert(T toInsert) {
        return toInsert.compareTo(element) < 0
                ? changeLeft(left.insert(toInsert))
                : changeRight(right.insert(toInsert));
    }

    @Override
    public BinarySearchTree<T> remove(T toRemove) {
        int comparison = toRemove.compareTo(element);

        if (comparison == 0) {
            if (isNull(left)) {
                return right;
            } else if (isNull(right)) {
                return left;
            }

            T max = left.max();

            return changeElement(max).changeLeft(left.remove(max));
        }

        return comparison < 0
                ? changeLeft(left.remove(toRemove))
                : changeRight(right.remove(toRemove));
    }

    @Override
    public boolean contains(T toCheck) {
        int comparison = toCheck.compareTo(element);

        return comparison == 0 || (comparison < 0 ? left : right).contains(toCheck);
    }

    @Override
    public T min() {
        return isNull(left) ? element : left.min();
    }

    @Override
    public T max() {
        return isNull(right) ? element : right.max();
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left, element, right);
    }

    private BinarySearchTreeImpl(T element, BinarySearchTree<T> left, BinarySearchTree<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }

    private static <T extends Comparable<T>> BinarySearchTree<T> leaf(T element) {
        return new BinarySearchTreeImpl<>(element, new NullNode<>(), new NullNode<>());
    }

    private boolean isNull(BinarySearchTree<T> toCheck) {
        return toCheck instanceof NullNode;
    }

    private BinarySearchTreeImpl<T> changeLeft(BinarySearchTree<T> left) {
        return new BinarySearchTreeImpl<>(element, left, right);
    }

    private BinarySearchTreeImpl<T> changeRight(BinarySearchTree<T> right) {
        return new BinarySearchTreeImpl<>(element, left, right);
    }

    private BinarySearchTreeImpl<T> changeElement(T element) {
        return new BinarySearchTreeImpl<>(element, left, right);
    }

    private static class NullNode<T extends Comparable<T>> implements BinarySearchTree<T> {
        @Override
        public BinarySearchTree<T> insert(T toInsert) {
            return leaf(toInsert);
        }

        @Override
        public BinarySearchTree<T> remove(T toRemove) {
            return this;
        }

        @Override
        public boolean contains(T toCheck) {
            return false;
        }

        public String toString() {
            return "";
        }

        @Override
        public T min() {
            return null;
        }

        @Override
        public T max() {
            return null;
        }
    }
}
