package demos.aop.test.one;

import java.util.Collection;
import java.util.Iterator;

public class TestAlpha implements Collection<String> {
    public TestAlpha() {
        System.out.println("Call to TestAlpha.<init>");
    }

    @Override
    public int size() {
        System.out.println("Call to TestAlpha.size");
        return 0;
    }

    @Override
    public boolean isEmpty() {
        System.out.println("Call to TestAlpha.isEmpty");
        return false;
    }

    @Override
    public boolean contains(Object o) {
        System.out.println("Call to TestAlpha.contains");
        return false;
    }

    @Override
    public Iterator<String> iterator() {
        System.out.println("Call to TestAlpha.iterator");
        return new Iterator<String>() {
            public boolean hasNext() {
                return false;
            }

            @Override
            public String next() {
                return null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        System.out.println("Call to TestAlpha.toArray()");
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        System.out.println("Call to TestAlpha.toArray(T[])");
        return null;
    }

    @Override
    public boolean add(String e) {
        System.out.println("Call to TestAlpha.add");
        return false;
    }

    @Override
    public boolean remove(Object o) {
        System.out.println("Call to TestAlpha.remove");
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        System.out.println("Call to TestAlpha.containsAll");
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        System.out.println("Call to TestAlpha.addAll");
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        System.out.println("Call to TestAlpha.removeAll");
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        System.out.println("Call to TestAlpha.retainAll");
        return false;
    }

    @Override
    public void clear() {
        System.out.println("Call to TestAlpha.clear");
    }

}
