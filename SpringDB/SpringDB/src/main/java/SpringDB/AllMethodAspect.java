package SpringDB;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AllMethodAspect {
    @Before("execution(* SpringDB.PerformerService.*(..))")
    public void logAllMethods(JoinPoint joinPoint){
        System.out.println("Вариант логирования execution. Был вызван метод " + joinPoint.getSignature().toString());
    }
}
