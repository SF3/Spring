package demos.aop.test.two;

import java.util.Collection;
import java.util.Iterator;

public class TestBravo implements Collection<String> {
    public TestBravo() {
        System.out.println("Call to TestBravo.<init>");
    }

    @Override
    public int size() {
        System.out.println("Call to TestBravo.size");
        return 0;
    }

    @Override
    public boolean isEmpty() {
        System.out.println("Call to TestBravo.isEmpty");
        return false;
    }

    @Override
    public boolean contains(Object o) {
        System.out.println("Call to TestBravo.contains");
        return false;
    }

    @Override
    public Iterator<String> iterator() {
        System.out.println("Call to TestBravo.iterator");
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
        System.out.println("Call to TestBravo.toArray()");
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        System.out.println("Call to TestBravo.toArray(T[])");
        return null;
    }

    @Override
    public boolean add(String e) {
        System.out.println("Call to TestBravo.add");
        return false;
    }

    @Override
    public boolean remove(Object o) {
        System.out.println("Call to TestBravo.remove");
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        System.out.println("Call to TestBravo.containsAll");
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        System.out.println("Call to TestBravo.addAll");
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        System.out.println("Call to TestBravo.removeAll");
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        System.out.println("Call to TestBravo.retainAll");
        return false;
    }

    @Override
    public void clear() {
        System.out.println("Call to TestBravo.clear");
    }

}
