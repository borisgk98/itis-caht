package ru.kpfu.itis.borisgk98.chat.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import ru.kpfu.itis.borisgk98.chat.exception.IllegalAspectTargetException;
import ru.kpfu.itis.borisgk98.chat.service.WSMessageService;

import java.lang.reflect.Method;

@Component
@Aspect
@RequiredArgsConstructor
@Log4j2
public class SocketErrorHandlingAspect {

    private final ObjectMapper om;
    private final WSMessageService wsMessageService;

    @Pointcut("@annotation(ru.kpfu.itis.borisgk98.chat.annotation.SocketHandler)")
    private void pointcut() {}

    @Around("pointcut()")
    private void aroundAdvise(ProceedingJoinPoint call) throws Throwable{
        MethodSignature signature = (MethodSignature) call.getSignature();
        Method method = signature.getMethod();

        if (call.getArgs().length < 1) {
            log.error("Illegal aspect target: method was annotated with {0} " +
                    "should accept first argument (WebSocketSession session)", method.getDeclaredAnnotations().toString());
            throw new IllegalAspectTargetException();
        }
        WebSocketSession session = (WebSocketSession) call.getArgs()[0];
        try {
            call.proceed();
        } catch (Exception e) {
            log.error("Error while call method: " + method.getDeclaringClass() + "." + method.getName());
            e.printStackTrace();
            try {
                session.sendMessage(wsMessageService.buildAsTextMessage(e));
            } catch (Exception e2) {
                log.error("Error while send error message");
            }
        }
    }
}
