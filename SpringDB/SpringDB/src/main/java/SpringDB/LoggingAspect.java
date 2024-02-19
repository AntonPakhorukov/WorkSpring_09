package SpringDB;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Aspect
@Component
public class LoggingAspect {
    @After("@annotation(SpringDB.TrackUserAction)")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
//        System.out.println("Вариант логирования через annotation. Method " + joinPoint.getSignature().getName() + " was called");
        try{
            FileWriter fileWriter = new FileWriter("logByAnnotation.txt", true);
            fileWriter.write("Method " + joinPoint.getSignature().getName() + " was called\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
