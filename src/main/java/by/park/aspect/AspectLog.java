package by.park.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectLog {
    private static final Logger log = Logger.getLogger(AspectLog.class);

    @Pointcut("execution(* by.park.dao.repository.UserRepository.*(..))")
    public void aroundRepositoryPointcut() {
    }

    @Around("aroundRepositoryPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(this);
        Object proceed = joinPoint.proceed();
        log.info(this);
        return proceed;
    }

}
