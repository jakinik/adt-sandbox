package edu.jakinik.adt.bst;

import org.hamcrest.CoreMatchers;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static edu.jakinik.adt.bst.BinarySearchTreeImpl.withRoot;
import static org.junit.Assert.assertThat;

public class BinarySearchTreeImplTest {

    @Test
    public void singleElementTree() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(1);

        assertThat(tree, contains(1));
        assertThat(tree, containsNone(2, -1));
    }

    @Test
    public void lessTrivial() throws Exception {
        BinarySearchTree<Integer>
                tree = withRoot(123).insert(4).insert(125).insert(2).insert(3);

        assertThat(tree, containsAll(2, 3, 4, 123, 125));
    }

    @Test
    public void removeLeaf() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(3).insert(2).insert(1).insert(4);

        assertThat(tree.remove(1), containsAll(3, 2, 4));
        assertThat(tree.remove(1), CoreMatchers.not(contains(1)));

        assertThat(tree.remove(4), containsAll(3, 2, 1));
        assertThat(tree.remove(4), CoreMatchers.not(contains(4)));
    }

    @Test
    public void removeNodeWithSingleChild() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(3).insert(2).insert(1).insert(4);

        assertThat(tree.remove(2), containsAll(3, 1, 4));
        assertThat(tree.remove(2), CoreMatchers.not(contains(2)));
    }

    @Test
    public void removeNodeWithTwoChildren() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(5).insert(3).insert(2).insert(4);

        assertThat(tree.remove(4), containsAll(5, 3, 2));
        assertThat(tree.remove(4), CoreMatchers.not(contains(4)));
    }

    @Test
    public void removeNodeWithTwoChildrenWithDeepMaxFind() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(7).insert(5).insert(6).insert(2).insert(3).insert(4);

        assertThat(tree.remove(5), containsAll(7, 6, 2, 3, 4));
        assertThat(tree.remove(5), CoreMatchers.not(contains(5)));
    }

    @Test
    public void removeManyTimes() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(7)
                .insert(5).insert(6).insert(2).insert(3).insert(4)
                .insert(9).insert(8).insert(10)
                .remove(9).remove(8).remove(3);

        assertThat(tree, containsAll(7, 5, 6, 2, 4, 10));
        assertThat(tree, containsNone(9, 8, 3));
    }

    @Test
    public void removeElementNorPresentInTree() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(3).insert(1).insert(2).remove(4).remove(0).remove(5);

        assertThat(tree, containsAll(3, 2, 1));
        assertThat(tree, containsNone(4, 0, 5));
    }

    @Test
    public void removeRoot() throws Exception {
        BinarySearchTree<Integer> tree = withRoot(7)
                .insert(5).insert(6).insert(2).insert(3).insert(4)
                .insert(9).insert(8).insert(10)
                .remove(7);

        assertThat(tree, containsAll(5, 6, 2, 3, 4, 9, 8, 10));
        assertThat(tree, CoreMatchers.not(contains(7)));
    }

    private static <T extends Comparable<T>> Matcher<BinarySearchTree<T>> contains(T element) {
        return new CustomTypeSafeMatcher<BinarySearchTree<T>>("bst contains " + element) {
            @Override
            protected boolean matchesSafely(BinarySearchTree<T> tBinarySearchTree) {
                return tBinarySearchTree.contains(element);
            }
        };
    }

    private static <T extends Comparable<T>> Matcher<BinarySearchTree<T>> containsAll(T... elements) {
        return allOf(
                asList(elements).stream()
                        .map(BinarySearchTreeImplTest::contains)
                        .collect(Collectors.toList()));
    }

    private static <T extends Comparable<T>> Matcher<BinarySearchTree<T>> containsNone(T... elements) {
        return allOf(
                asList(elements).stream()
                        .map(e -> CoreMatchers.not(contains(e)))
                        .collect(Collectors.toList()));
    }
}
