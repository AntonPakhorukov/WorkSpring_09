package SpringDB;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Aspect
@Component
public class AllMethodAspect {
    @Before("execution(* SpringDB.PerformerService.*(..))")
    public void logAllMethods(JoinPoint joinPoint){
//        System.out.println("Вариант логирования execution. Был вызван метод " + joinPoint.getSignature().toString());
        try{
            FileWriter fileWriter = new FileWriter("logByExecution.txt", true);
            fileWriter.write("Method " + joinPoint.getSignature().getName() + " was called\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
