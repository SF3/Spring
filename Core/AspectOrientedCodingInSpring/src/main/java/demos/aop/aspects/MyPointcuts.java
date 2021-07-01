package demos.aop.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class MyPointcuts {
    @Pointcut("within(demos.aop.test..*)")
    public void anyJoinPointInTestPackages() {
    }

    @Pointcut("this(java.util.Collection)")
    public void anyJoinPointInCollection() {
    }

    @Pointcut("args(java.lang.String,java.lang.Integer)")
    public void anyJoinPointTakingStringAndInteger() {
    }

    @Pointcut("execution(* demos.aop.test.one.*.*(..))")
    public void anyJoinPointInFirstTestPackage() {
    }

    @Pointcut("execution(* demos.aop.test.two.*.*(..))")
    public void anyJoinPointInSecondTestPackage() {
    }

    @Pointcut("execution(* demos.aop.test.three.*.*(..))")
    public void anyJoinPointInThirdTestPackage() {
    }

    @Pointcut("args(param1)")
    public void parameterPassing(String param1) {
    }
}
