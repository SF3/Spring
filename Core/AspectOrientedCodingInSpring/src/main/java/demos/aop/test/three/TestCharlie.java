package demos.aop.test.three;

import java.util.Collection;
import java.util.Iterator;

public class TestCharlie implements Collection<String> {
    public TestCharlie() {
        System.out.println("Call to TestCharlie.<init>");
    }


    @Override
    public int size() {
        System.out.println("Call to TestCharlie.size");
        return 0;
    }

    @Override
    public boolean isEmpty() {
        System.out.println("Call to TestCharlie.isEmpty");
        return false;
    }

    @Override
    public boolean contains(Object o) {
        System.out.println("Call to TestCharlie.contains");
        return false;
    }

    @Override
    public Iterator<String> iterator() {
        System.out.println("Call to TestCharlie.iterator");
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
        System.out.println("Call to TestCharlie.toArray()");
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        System.out.println("Call to TestCharlie.toArray(T[])");
        return null;
    }

    @Override
    public boolean add(String e) {
        System.out.println("Call to TestCharlie.add");
        return false;
    }

    @Override
    public boolean remove(Object o) {
        System.out.println("Call to TestCharlie.remove");
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        System.out.println("Call to TestCharlie.containsAll");
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        System.out.println("Call to TestCharlie.addAll");
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        System.out.println("Call to TestCharlie.removeAll");
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        System.out.println("Call to TestCharlie.retainAll");
        return false;
    }

    @Override
    public void clear() {
        System.out.println("Call to TestCharlie.clear");
    }

}
