package SpringDB;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @After("@annotation(SpringDB.TrackUserAction)")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        System.out.println("Вариант логирования через annotation. Method " + joinPoint.getSignature().getName() + " was called");
    }
}
