package demos.aop.startup;

import java.io.PrintStream;
import java.util.Collection;

public class MyBean {
    private Collection<String> testOne;
    private Collection<String> testTwo;
    private Collection<String> testThree;

    public Collection<String> getTestOne() {
        return testOne;
    }

    public void setTestOne(Collection<String> testOne) {
        this.testOne = testOne;
    }

    public Collection<String> getTestTwo() {
        return testTwo;
    }

    public void setTestTwo(Collection<String> testTwo) {
        this.testTwo = testTwo;
    }

    public Collection<String> getTestThree() {
        return testThree;
    }

    public void setTestThree(Collection<String> testThree) {
        this.testThree = testThree;
    }

    public void doTests() {
        PrintStream cout = System.out;

        cout.println("Testing " + testOne.getClass().getName());
        cout.println("\t 'isEmpty' returns " + testOne.isEmpty());
        cout.println("\t 'remove' returns " + testOne.remove("foo"));
        cout.println("\t 'add' returns " + testOne.add("foo"));
        cout.println("\t 'toArray' returns " + testOne.toArray());
        cout.println("\t 'size' returns " + testOne.size());

        cout.println("Testing " + testTwo.getClass().getName());
        cout.println("\t 'isEmpty' returns " + testTwo.isEmpty());
        cout.println("\t 'remove' returns " + testTwo.remove("foo"));
        cout.println("\t 'add' returns " + testTwo.add("foo"));
        cout.println("\t 'toArray' returns " + testTwo.toArray());
        cout.println("\t 'size' returns " + testTwo.size());

        cout.println("Testing " + testThree.getClass().getName());
        cout.println("\t 'isEmpty' returns " + testThree.isEmpty());
        cout.println("\t 'remove' returns " + testThree.remove("foo"));
        cout.println("\t 'add' returns " + testThree.add("foo"));
        cout.println("\t 'toArray' returns " + testThree.toArray());
        cout.println("\t 'size' returns " + testThree.size());
    }
}
