package demos.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class MyAspect {
    @After("MyPointcuts.anyJoinPointInTestPackages()")
    public void eg1() {
        System.out.println("\t\tAfter Advice 1");
    }

    @Before("MyPointcuts.anyJoinPointInTestPackages()")
    public void eg2() {
        System.out.println("\t\tBefore Advice 1");
    }

    @After("MyPointcuts.anyJoinPointInCollection()")
    public void eg3() {
        System.out.println("\t\tAfter Advice 2");
    }

    @Before("MyPointcuts.anyJoinPointInCollection()")
    public void eg4() {
        System.out.println("\t\tBefore Advice 2");
    }

    @After("MyPointcuts.anyJoinPointTakingStringAndInteger()")
    public void eg5() {
        System.out.println("\t\tAfter Advice 3");
    }

    @Before("MyPointcuts.anyJoinPointTakingStringAndInteger()")
    public void eg6() {
        System.out.println("\t\tBefore Advice 3");
    }

    @After("MyPointcuts.anyJoinPointInFirstTestPackage()")
    public void eg7() {
        System.out.println("\t\tAfter Advice 4");
    }

    @Before("MyPointcuts.anyJoinPointInFirstTestPackage()")
    public void eg8() {
        System.out.println("\t\tBefore Advice 4");
    }

    @After("MyPointcuts.anyJoinPointInSecondTestPackage()")
    public void eg9() {
        System.out.println("\t\tAfter Advice 5");
    }

    @Before("MyPointcuts.anyJoinPointInSecondTestPackage()")
    public void eg10() {
        System.out.println("\t\tBefore Advice 5");
    }

    @After("MyPointcuts.anyJoinPointInThirdTestPackage()")
    public void eg11() {
        System.out.println("\t\tAfter Advice 6");
    }

    @Before("MyPointcuts.anyJoinPointInThirdTestPackage()")
    public void eg12() {
        System.out.println("\t\tBefore Advice 6");
    }

    @After("MyPointcuts.parameterPassing(param1)")
    public void eg13(String param1) {
        System.out.println("\t\tAfter Advice 7 " + param1);
    }

    @Before("MyPointcuts.parameterPassing(param1)")
    public void eg14(String param1) {
        System.out.println("\t\tBefore Advice 7 " + param1);
    }

    @Around("MyPointcuts.anyJoinPointInTestPackages()")
    public Object eg15(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature targetMethod = (MethodSignature) pjp.getSignature();
        Method actualMethod = pjp.getTarget().getClass().getMethod(targetMethod.getName(), targetMethod.getParameterTypes());

        String targetMethodStr = targetMethod.toShortString();
        String actualMethodStr = actualMethod.toGenericString();

        System.out.printf("\t\tIntercepted call to %s (actually '%s')\n", targetMethodStr, actualMethodStr);

        Object originalRetval = pjp.proceed();

        if (targetMethod.getReturnType() == Integer.TYPE) {
            return 123;
        }
        return originalRetval;
    }
}
